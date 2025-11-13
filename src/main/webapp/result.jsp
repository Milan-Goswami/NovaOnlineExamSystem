<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.nova.model.Question" %>
<%@ page import="com.nova.servlet.ServletHelpers" %>
<!DOCTYPE html>
<!--
  Result page: shares a quick performance summary and question-by-question review in stacked cards.
  Everything wraps cleanly so long text remains readable even on narrow screens.
-->
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Exam Result • Nova Online Examination System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  </head>
  <body class="dashboard-page">
  <%
    if (ServletHelpers.isTeacherSessionOnly(request)) {
        response.sendRedirect(request.getContextPath() + "/view-questions");
        return;
    }
    if (!ServletHelpers.isStudentLoggedIn(request)) {
        response.sendRedirect(request.getContextPath() + "/student_login.html?error=unauthorized");
        return;
    }

    Integer score = (Integer) request.getAttribute("score");
    Integer total = (Integer) request.getAttribute("total");
    int s = score != null ? score : 0;
    int t = total != null ? total : 0;
    int wrong = Math.max(0, t - s);
    int percent = (t == 0) ? 0 : (s * 100 / t);
    String message = percent >= 80 ? "Excellent work!" : "Review and try again.";

    @SuppressWarnings("unchecked")
    List<Question> questions = (List<Question>) session.getAttribute("questions");
  %>
    <div class="page-shell">
      <%@ include file="/WEB-INF/jsp/nav.jsp" %>

      <main class="dashboard-main">
        <section class="card wide">
          <h1>Exam Summary</h1>
          <p class="supporting-text">
            Here is a quick breakdown of your performance. Review every question below to see the correct answer.
          </p>

          <div class="summary-grid">
            <article class="summary-card">
              <span class="summary-label">Total Score</span>
              <span class="summary-value"><%= s %> / <%= t %></span>
            </article>
            <article class="summary-card">
              <span class="summary-label">Accuracy</span>
              <span class="summary-value"><%= percent %>%</span>
            </article>
            <article class="summary-card">
              <span class="summary-label">Result</span>
              <span class="summary-value"><%= message %></span>
            </article>
          </div>

          <div class="actions">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/student_dashboard.jsp">Back to Dashboard</a>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/start-exam">Retake Exam</a>
          </div>

          <h2>Question Review</h2>
          <div class="card-list">
          <%
            if (questions != null) {
                int index = 1;
                for (Question q : questions) {
                    String userAns = request.getParameter("q_" + q.getId());
                    String userDisplay = (userAns != null && !userAns.isBlank()) ? userAns.toUpperCase() : "Not answered";
                    String correct = q.getCorrectOption();
                    boolean isCorrect = userAns != null && userAns.equalsIgnoreCase(correct);
          %>
            <article class="question-card">
              <header class="question-card__header">
                <p class="question-title">
                  Q<%= index++ %>: <%= q.getQuestionText() %>
                </p>
                <span class="badge <%= isCorrect ? "badge-success" : "" %>">
                  <%= isCorrect ? "Correct" : "Review" %>
                </span>
              </header>
              <div class="option-list">
                <div class="option-item">
                  <span><strong>Your answer:</strong> <%= userDisplay %></span>
                </div>
                <div class="option-item">
                  <span><strong>Correct option:</strong> <%= correct %></span>
                </div>
              </div>
            </article>
          <%
                }
                session.removeAttribute("questions");
            } else {
          %>
            <article class="question-card">
              <p class="question-title">Questions are not available for review.</p>
              <p class="supporting-text">Start a new exam to load the question list again.</p>
            </article>
          <%
            }
          %>
          </div>
        </section>
      </main>

      <footer class="site-footer">
        <span>Nova Online Exam System – MCA Project</span>
      </footer>
    </div>

    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
  </body>
</html>
