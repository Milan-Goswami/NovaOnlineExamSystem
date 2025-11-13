package com.nova.servlet;

import com.nova.dao.TeacherDAO;
import com.nova.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * TeacherRegisterServlet handles POST /teacher-register.
 * It creates a new teacher record using TeacherDAO.
 */
@WebServlet("/teacher-register")
public class TeacherRegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (ServletHelpers.isTeacherLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/view-questions");
            return;
        }
        if (ServletHelpers.isStudentLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/student_dashboard.jsp");
            return;
        }

        String name = safeTrim(request.getParameter("name"));
        String email = safeTrim(request.getParameter("email"));
        String password = safeTrim(request.getParameter("password"));

        if (isBlank(name) || isBlank(email) || isBlank(password)) {
            sendError(response, request.getContextPath(), "All fields are required. Please go back and fill every field.");
            return;
        }

        Teacher teacher = new Teacher(name, email, password);
        TeacherDAO dao = new TeacherDAO();
        boolean created = dao.registerTeacher(teacher);

        if (created) {
            response.sendRedirect(request.getContextPath() + "/teacher_login.html?status=registered");
        } else {
            response.sendRedirect(request.getContextPath() + "/teacher_register.html?error=duplicate");
        }
    }

    private String safeTrim(String value) {
        return value != null ? value.trim() : "";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void sendError(HttpServletResponse response, String contextPath, String message) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html><html><body>");
            out.println("<p style='color:red; font-weight:bold;'>" + message + "</p>");
            out.println("<p><a href='" + contextPath + "/teacher_register.html'>Back to registration</a></p>");
            out.println("</body></html>");
        }
    }
}

