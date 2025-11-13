package com.nova.servlet; // Package for servlet classes

import com.nova.dao.StudentDAO; // DAO to interact with the student table
import com.nova.model.Student; // Model representing student data

import jakarta.servlet.ServletException; // For signaling servlet-related errors
import jakarta.servlet.http.HttpServlet; // Base class for HTTP servlets
import jakarta.servlet.http.HttpServletRequest; // Represents the incoming HTTP request
import jakarta.servlet.http.HttpServletResponse; // Represents the HTTP response to send back
import java.io.IOException; // For I/O exceptions

/**
 * RegisterServlet handles POST requests to register a new student.
 * Flow (beginner-friendly):
 * 1) Browser submits a form with name, email, password to /register (POST)
 * 2) Servlet reads form fields from the request
 * 3) It uses StudentDAO to insert the new record into the database
 * 4) If success, it redirects to student_login.html so the user can log in
 * 5) If failure, it writes a simple error message to the browser
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Read form parameters by their input names
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Build a Student object from the input
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPassword(password); // In production, hash before saving

        // Use DAO to save the student
        StudentDAO dao = new StudentDAO();
        boolean ok = dao.registerStudent(student);

        if (ok) {
            // Redirect tells the browser to make a new GET request to student_login.html
            response.sendRedirect(request.getContextPath() + "/student_login.html?status=registered");
        } else {
            // If insert failed, show a simple message
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().println("Registration failed. Please try again.");
        }
    }
}


