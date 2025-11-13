package com.nova.servlet; // Package for servlet classes

import com.nova.dao.StudentDAO; // DAO for students
import com.nova.model.Student; // Student model

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // To store user info across requests
import java.io.IOException;

/**
 * LoginServlet handles POST /login.
 * Flow:
 * 1) Read email and password from the form submission
 * 2) Use StudentDAO.login to validate credentials
 * 3) If valid, create/get an HTTP session and store minimal user info
 * 4) Forward or redirect to a dashboard page
 * 5) If invalid, render a simple error message
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        StudentDAO dao = new StudentDAO();
        Student s = dao.login(email, password);

        if (s != null) {
            // Successful login: create or get the session and store user info
            HttpSession session = request.getSession(true);
            session.removeAttribute("teacherId");
            session.removeAttribute("teacherName");
            session.removeAttribute("teacherEmail");
            session.setAttribute("studentName", s.getName());
            session.setAttribute("studentId", s.getId());

            // Redirect to dashboard JSP (the JSP should exist in webapp root)
            response.sendRedirect(request.getContextPath() + "/student_dashboard.jsp");
        } else {
            // Invalid login: set a message and display it
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<!DOCTYPE html><html><body>");
            response.getWriter().println("<p style='color:red'>Invalid login</p>");
            response.getWriter().println("<p><a href='" + request.getContextPath() + "/student_login.html'>Back to Login</a></p>");
            response.getWriter().println("</body></html>");
        }
    }
}


