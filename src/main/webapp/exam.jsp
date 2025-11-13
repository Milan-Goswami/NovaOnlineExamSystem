<%@ page import="java.util.List" %>
<%@ page import="com.nova.model.Question" %>
<%@ page import="com.nova.servlet.ServletHelpers" %>
<!-- Beginner note: This page shows each question inside a flexible card and keeps the timer visible at the top. -->
<%
  if (ServletHelpers.isTeacherSessionOnly(request)) {
      response.sendRedirect(request.getContextPath() + "/view-questions");
      return;
  }
  if (!ServletHelpers.isStudentLoggedIn(request)) {
      response.sendRedirect(request.getContextPath() + "/student_login.html?error=unauthorized");
      return;
  }

  @SuppressWarnings("unchecked")
  List<Question> questions = (List<Question>) session.getAttribute("questions");
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Exam • Nova Online Exam</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" />
  </head>
  <body class="exam-page">
    <%@ include file="/WEB-INF/jsp/nav.jsp" %>

    <main class="page-content">
      <section class="exam-wrapper">
        <header class="exam-header">
          <div class="exam-heading">
            <h1>Timed Assessment</h1>
            <p>Answer each question carefully. Long text wraps automatically so you can focus on choosing the best answer.</p>
          </div>
          <div class="timer-panel" data-timer data-duration="600" data-form="examForm">
            <svg class="timer-ring" viewBox="0 0 120 120" role="presentation" aria-hidden="true">
              <circle class="timer-ring__background" cx="60" cy="60" r="54"></circle>
              <circle class="timer-ring__progress" cx="60" cy="60" r="54"></circle>
            </svg>
            <div class="timer-labels">
              <span class="timer-time" data-timer-text>10:00</span>
              <span class="timer-fallback">Time Remaining</span>
            </div>
          </div>
        </header>

        <form id="examForm" class="exam-form" method="post" action="<%= request.getContextPath() %>/submit-exam">
          <div class="question-stack">
          <%
            if (questions != null && !questions.isEmpty()) {
                int idx = 1;
                for (Question q : questions) {
          %>
            <article class="question-card" aria-labelledby="question-<%= q.getId() %>">
              <header class="question-card__header">
                <h2 id="question-<%= q.getId() %>">Question <%= idx++ %></h2>
              </header>
              <p class="question-card__text"><%= q.getQuestionText() %></p>
              <div class="option-list" role="group" aria-label="Answer choices">
                <label class="option-item">
                  <input type="radio" name="q_<%= q.getId() %>" value="A" required />
                  <span><strong>A)</strong> <%= q.getOptionA() %></span>
                </label>
                <label class="option-item">
                  <input type="radio" name="q_<%= q.getId() %>" value="B" />
                  <span><strong>B)</strong> <%= q.getOptionB() %></span>
                </label>
                <label class="option-item">
                  <input type="radio" name="q_<%= q.getId() %>" value="C" />
                  <span><strong>C)</strong> <%= q.getOptionC() %></span>
                </label>
                <label class="option-item">
                  <input type="radio" name="q_<%= q.getId() %>" value="D" />
                  <span><strong>D)</strong> <%= q.getOptionD() %></span>
                </label>
              </div>
            </article>
          <%
                }
            } else {
          %>
            <article class="question-card">
              <p class="question-card__text">No questions loaded. Please <a class="text-link" href="<%= request.getContextPath() %>/start-exam">reload the exam</a> or contact your teacher.</p>
            </article>
          <%
            }
          %>
          </div>

          <div class="form-actions">
            <button type="submit" class="btn btn-primary">Submit Exam</button>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/student_dashboard.jsp">Return to Dashboard</a>
          </div>
        </form>
      </section>
    </main>

    <script src="<%= request.getContextPath() %>/js/theme.js"></script>
    <script src="<%= request.getContextPath() %>/js/timer.js"></script>
  </body>
</html>
