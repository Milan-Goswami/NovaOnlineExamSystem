package com.nova.servlet; // Package for servlet classes

import com.nova.dao.QuestionDAO; // DAO to load questions
import com.nova.model.Question; // Question model

import jakarta.servlet.RequestDispatcher; // To forward to JSP
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // To store questions for the exam flow
import java.io.IOException;
import java.util.List; // To hold the list of questions

/**
 * StartExamServlet loads all questions and forwards to exam.jsp.
 * Flow:
 * 1) Client hits /start-exam (GET)
 * 2) Servlet loads all questions from the database via DAO
 * 3) Stores the list in the session so SubmitExam can reference them later
 * 4) Forwards the request to exam.jsp for rendering the questions
 */
public class StartExamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!ServletHelpers.requireStudentLogin(request, response)) {
            return;
        }

        QuestionDAO dao = new QuestionDAO();
        List<Question> questions = dao.getAllQuestions();

        // Save questions in the session for later grading
        HttpSession session = request.getSession(true);
        session.setAttribute("questions", questions);

        // Forward internally to exam.jsp (the JSP should iterate over the questions)
        RequestDispatcher rd = request.getRequestDispatcher("/exam.jsp");
        rd.forward(request, response);
    }
}


