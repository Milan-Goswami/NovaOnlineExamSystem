package com.nova.servlet;

import com.nova.dao.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * DeleteQuestionServlet handles GET /delete-question?id=123.
 * It removes the question with the provided ID.
 */
@WebServlet("/delete-question")
public class DeleteQuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!ServletHelpers.requireTeacherLogin(request, response)) {
            return;
        }

        int id = parseId(request.getParameter("id"));
        QuestionDAO dao = new QuestionDAO();
        boolean deleted = dao.deleteQuestion(id);

        if (deleted) {
            response.sendRedirect(request.getContextPath() + "/view-questions?status=deleted");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html><html><body>");
                out.println("<p style='color:red;'>Unable to delete the question. It may not exist.</p>");
                out.println("<p><a href='" + request.getContextPath() + "/teacher_dashboard.jsp'>Back to dashboard</a></p>");
                out.println("</body></html>");
            }
        }
    }

    private int parseId(String idParam) {
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

