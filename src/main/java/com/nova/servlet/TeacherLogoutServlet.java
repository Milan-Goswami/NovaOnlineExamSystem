package com.nova.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * TeacherLogoutServlet handles GET /teacher-logout.
 * It simply invalidates the session and redirects to the login page.
 */
@WebServlet("/teacher-logout")
public class TeacherLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Remove all stored attributes
        }

        response.sendRedirect(request.getContextPath() + "/teacher_login.html?status=loggedout");
    }
}

