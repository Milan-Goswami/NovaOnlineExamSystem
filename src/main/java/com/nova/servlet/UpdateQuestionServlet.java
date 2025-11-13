package com.nova.servlet;

import com.nova.dao.QuestionDAO;
import com.nova.model.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * UpdateQuestionServlet handles POST /update-question.
 * It updates existing question records using the DAO layer.
 */
@WebServlet("/update-question")
public class UpdateQuestionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!ServletHelpers.requireTeacherLogin(request, response)) {
            return;
        }

        // Parse the numeric ID safely
        int id = parseId(request.getParameter("id"));

        Question question = new Question();
        question.setId(id);
        question.setQuestionText(request.getParameter("questionText"));
        question.setOptionA(request.getParameter("optionA"));
        question.setOptionB(request.getParameter("optionB"));
        question.setOptionC(request.getParameter("optionC"));
        question.setOptionD(request.getParameter("optionD"));
        String correctOption = request.getParameter("correctOption");
        question.setCorrectOption(correctOption != null ? correctOption.toUpperCase() : null);

        QuestionDAO dao = new QuestionDAO();
        boolean updated = dao.updateQuestion(question);

        if (updated) {
            response.sendRedirect(request.getContextPath() + "/view-questions?status=updated");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html><html><body>");
                out.println("<p style='color:red;'>Unable to update the question. Please try again.</p>");
                out.println("<p><a href='" + request.getContextPath() + "/update_question.jsp?id=" + id + "'>Back to form</a></p>");
                out.println("</body></html>");
            }
        }
    }

    /**
     * Converts the id string to an int, returning 0 if parsing fails.
     * Returning 0 is acceptable because the DAO will fail to update and show an error.
     */
    private int parseId(String idParam) {
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

