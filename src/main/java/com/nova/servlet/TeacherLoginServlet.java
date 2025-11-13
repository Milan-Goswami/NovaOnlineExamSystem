package com.nova.servlet; // Package containing all servlet controllers

import com.nova.dao.TeacherDAO;
import com.nova.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * TeacherLoginServlet handles POST /teacher-login requests.
 * It validates teacher credentials and stores basic info in the session.
 */
@WebServlet("/teacher-login")
public class TeacherLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read login form fields by the names defined in teacher_login.html
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = teacherDAO.loginTeacher(email, password);

        if (teacher != null) {
            // Successful login: create/get session and remember who logged in
            HttpSession session = request.getSession(true);
            session.removeAttribute("studentId");
            session.removeAttribute("studentName");
            session.setAttribute("teacherId", teacher.getId());
            session.setAttribute("teacherName", teacher.getName());
            session.setAttribute("teacherEmail", teacher.getEmail());

            // Redirect to the servlet that prepares the dashboard data
            response.sendRedirect(request.getContextPath() + "/view-questions");
        } else {
            // Invalid credentials -> show a simple beginner-friendly message
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html><head><title>Teacher Login</title></head><body>");
                out.println("<p style='color:red;'>Invalid login. Please try again.</p>");
                out.println("<p><a href='" + request.getContextPath() + "/teacher_login.html'>Back to Login</a></p>");
                out.println("</body></html>");
            }
        }
    }
}

