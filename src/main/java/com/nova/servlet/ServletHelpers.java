package com.nova.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Small utility class with shared helper methods for our servlets.
 * Keeping helpers here avoids repeating the same code in multiple servlets.
 */
public final class ServletHelpers {

    // Private constructor prevents instantiation (utility class pattern)
    private ServletHelpers() { }

    /**
     * Checks whether a teacher is logged in by looking at the session attributes.
     *
     * @param request current HTTP request
     * @return true if teacherId is present in the session
     */
    public static boolean isTeacherLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("teacherId") != null;
    }

    /**
     * Checks whether a student is logged in (used to protect student-only areas).
     *
     * @param request current HTTP request
     * @return true if studentId is present in the session
     */
    public static boolean isStudentLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("studentId") != null;
    }

    /**
     * Indicates that a teacher is logged in without a student session.
     * Helpful when we want to redirect teachers away from student-only pages.
     *
     * @param request current HTTP request
     * @return true if teacherId exists and studentId does not
     */
    public static boolean isTeacherSessionOnly(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        boolean teacherPresent = session.getAttribute("teacherId") != null;
        boolean studentPresent = session.getAttribute("studentId") != null;
        return teacherPresent && !studentPresent;
    }

    /**
     * Redirects to the appropriate page if no student is logged in.
     * @return true if the student session is present
     */
    public static boolean requireStudentLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isStudentLoggedIn(request)) {
            return true;
        }
        if (isTeacherSessionOnly(request)) {
            response.sendRedirect(request.getContextPath() + "/view-questions");
        } else {
            response.sendRedirect(request.getContextPath() + "/student_login.html?error=unauthorized");
        }
        return false;
    }

    /**
     * Ensures a teacher session exists and redirects otherwise.
     * @return true when a teacher is logged in exclusively
     */
    public static boolean requireTeacherLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isTeacherLoggedIn(request)) {
            if (isStudentLoggedIn(request)) {
                response.sendRedirect(request.getContextPath() + "/student_dashboard.jsp");
                return false;
            }
            return true;
        }
        if (isStudentLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/student_dashboard.jsp");
            return false;
        }
        response.sendRedirect(request.getContextPath() + "/teacher_login.html?error=unauthorized");
        return false;
    }
}

