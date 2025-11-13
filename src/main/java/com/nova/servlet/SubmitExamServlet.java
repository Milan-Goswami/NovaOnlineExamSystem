package com.nova.servlet; // Package for servlet classes

import com.nova.dao.ResultDAO; // DAO to save results
import com.nova.model.Question; // To read correct answers
import com.nova.model.Result; // To persist the score

import jakarta.servlet.RequestDispatcher; // Forward to result JSP
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // To read stored questions and user info
import java.io.IOException;
import java.util.List;

/**
 * SubmitExamServlet processes submitted answers, computes the score, saves it,
 * and forwards to result.jsp.
 * Flow:
 * 1) User submits the exam form to /submit-exam (POST)
 * 2) Servlet retrieves the questions from the session
 * 3) For each question, read the chosen answer from the request parameters
 *    (e.g., form field name: q_123 for question id 123)
 * 4) Compare with the correct option and count the score
 * 5) Save the score via ResultDAO using the studentId from session
 * 6) Put score in request scope and forward to result.jsp
 */
public class SubmitExamServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!ServletHelpers.requireStudentLogin(request, response)) {
            return;
        }

        HttpSession session = request.getSession(false); // Safe after checks above

        // Get the list of questions previously loaded
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        if (questions == null || questions.isEmpty()) {
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().println("No questions in session. Start the exam again.");
            return;
        }

        int score = 0; // Accumulate correct answers

        // For each question, get user's answer from the request and compare
        for (Question q : questions) {
            String paramName = "q_" + q.getId(); // Convention for input names in the exam form
            String userAnswer = request.getParameter(paramName); // e.g., "A", "B", "C", or "D"
            String correct = q.getCorrectOption();
            if (userAnswer != null && userAnswer.equalsIgnoreCase(correct)) {
                score++;
            }
        }

        // Persist the result using the logged-in student's id
        Object studentIdObj = session.getAttribute("studentId");
        if (studentIdObj instanceof Integer) {
            int studentId = (Integer) studentIdObj;
            Result r = new Result();
            r.setStudentId(studentId);
            r.setScore(score);
            new ResultDAO().saveResult(r); // Save; ignoring return for simplicity
        }

        // Make score available to the JSP and forward to show the result
        request.setAttribute("score", score);
        request.setAttribute("total", questions.size());
        RequestDispatcher rd = request.getRequestDispatcher("/result.jsp");
        rd.forward(request, response);
    }
}


