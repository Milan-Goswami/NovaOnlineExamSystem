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
 * AddQuestionServlet handles POST /add-question.
 * It reads the form data, creates a Question object, and saves it using the DAO.
 */
@WebServlet("/add-question")
public class AddQuestionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Only logged-in teachers can add questions
        if (!ServletHelpers.requireTeacherLogin(request, response)) {
            return;
        }

        // Read parameters sent from add_question.jsp
        String questionText = request.getParameter("questionText");
        String optionA = request.getParameter("optionA");
        String optionB = request.getParameter("optionB");
        String optionC = request.getParameter("optionC");
        String optionD = request.getParameter("optionD");
        String correctOption = request.getParameter("correctOption");

        // Create a Question object (no ID yet because it's auto-generated)
        Question question = new Question();
        question.setQuestionText(questionText);
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        question.setOptionC(optionC);
        question.setOptionD(optionD);
        question.setCorrectOption(correctOption != null ? correctOption.toUpperCase() : null);

        QuestionDAO dao = new QuestionDAO();
        boolean saved = dao.addQuestion(question);

        if (saved) {
            response.sendRedirect(request.getContextPath() + "/view-questions?status=added");
        } else {
            // Show a minimal error page for beginners
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html><html><body>");
                out.println("<p style='color:red;'>Unable to save the question. Please try again.</p>");
                out.println("<p><a href='" + request.getContextPath() + "/add_question.jsp'>Back to form</a></p>");
                out.println("</body></html>");
            }
        }
    }

}

