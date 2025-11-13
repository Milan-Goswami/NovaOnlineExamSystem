package com.nova.servlet;

import com.nova.dao.QuestionDAO;
import com.nova.model.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * ViewQuestionsServlet handles GET /view-questions.
 * It loads all questions and forwards the data to teacher_dashboard.jsp.
 */
@WebServlet("/view-questions")
public class ViewQuestionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!ServletHelpers.requireTeacherLogin(request, response)) {
            return;
        }

        QuestionDAO dao = new QuestionDAO();
        List<Question> questions = dao.getAllQuestions();

        // Store data in request scope (available only for this forward)
        request.setAttribute("questions", questions);

        // The JSP path is relative to src/main/webapp
        request.getRequestDispatcher("/teacher_dashboard.jsp").forward(request, response);
    }
}

