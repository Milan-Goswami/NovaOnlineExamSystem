<%@ page import="java.util.List" %>
<%@ page import="com.nova.dao.ResultDAO" %>
<%@ page import="com.nova.dao.QuestionDAO" %>
<%@ page import="com.nova.model.Result" %>
<%@ page import="com.nova.servlet.ServletHelpers" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!-- Beginner note: This dashboard pulls recent exam scores from the database and presents a friendly overview. -->
<%
  if (!ServletHelpers.isStudentLoggedIn(request)) {
      response.sendRedirect(request.getContextPath() + "/student_login.html?error=unauthorized");
      return;
  }

  Integer studentId = (Integer) session.getAttribute("studentId");
  String studentName = session.getAttribute("studentName") != null ? session.getAttribute("studentName").toString() : "Student";

  List<Result> results = java.util.Collections.emptyList();
  if (studentId != null) {
      results = new ResultDAO().getResultsByStudent(studentId);
  }

  int totalExams = results != null ? results.size() : 0;
  int bestScore = 0;
  int lastScore = 0;
  if (results != null && !results.isEmpty()) {
      lastScore = results.get(0).getScore();
      for (Result r : results) {
          if (r.getScore() > bestScore) {
              bestScore = r.getScore();
          }
      }
  }

   QuestionDAO questionDAO = new QuestionDAO();

   // Date formatter for DD/MM/YYYY HH:mm AM/PM format
  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Student Dashboard • Nova Online Exam</title>
    <link rel="stylesheet" href="css/style.css" />
  </head>
  <body class="dashboard-page">
    <%@ include file="/WEB-INF/jsp/nav.jsp" %>

    <main class="page-content">
      <section class="dashboard-layout">
        <div class="dashboard-header">
          <div class="dashboard-greeting">
            <h1>Hello, <%= studentName %> </h1>
            <p>Here is a quick summary of your progress. You can jump right into the next exam whenever you are ready.</p>
            <div class="dashboard-actions">
              <a class="btn btn-primary" href="<%= request.getContextPath() %>/start-exam">Start New Exam</a>
            </div>
          </div>
          <div class="dashboard-profile">
            <p class="dashboard-profile__label">Logged in as</p>
            <p class="dashboard-profile__name"><%= studentName %></p>
            <a class="btn btn-secondary btn--sm" href="<%= request.getContextPath() %>/logout.jsp">Logout</a>
          </div>
        </div>

        <div class="summary-grid">
          <article class="summary-card">
            <p class="summary-card__label">Total Exams Taken</p>
            <p class="summary-card__value"><%= totalExams %></p>
          </article>
          <article class="summary-card">
            <p class="summary-card__label">Best Score</p>
            <p class="summary-card__value"><%= bestScore %></p>
          </article>
          <article class="summary-card">
            <p class="summary-card__label">Last Score</p>
            <p class="summary-card__value"><%= lastScore %></p>
          </article>
        </div>

        <section class="card">
          <header class="card__header">
            <h2>Recent Exam History</h2>
            <p class="card__subtitle">Track how your performance has improved over time.</p>
          </header>
          <div class="table-wrapper">
            <table class="data-table">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Date</th>
                  <th scope="col">Score</th>
                  <th scope="col">Percent</th>
                </tr>
              </thead>
              <%
                if (results != null && !results.isEmpty()) {
                    int index = totalExams; // Start from total exams and count down
                    for (Result r : results) {
                        int correctAnswers = r.getScore();
                        // Get current total questions count from question table
                        int currentTotalQuestions = questionDAO.getTotalQuestionsCount();
                        
                        // Estimate total questions at exam time based on score
                        // If score > current questions, use score as reference
                        int totalQuestions = Math.max(currentTotalQuestions, correctAnswers);
                        if (totalQuestions == 0) totalQuestions = 10; // Default fallback
                        
                        // Calculate percentage: (correct answers / total questions) * 100
                        int percent = (correctAnswers * 100) / totalQuestions;
                        percent = Math.max(0, Math.min(100, percent)); // Ensure 0-100 range
                        
                        String examDate = "Not recorded";
                        if (r.getExamDate() != null) {
                            examDate = dateFormat.format(r.getExamDate());
                        }
              %>
                 <tr>
                  <td data-label="#"><%= index-- %></td>
                  <td data-label="Date"><%= examDate %></td>
                  <td data-label="Score"><%= correctAnswers %> / <%= totalQuestions %></td>
                  <td data-label="Percent"><%= percent %>%</td>
                </tr>
              <%
                    }
                } else {
              %>
                <tr>
                  <td data-label="Notice" colspan="4">No exam attempts yet. Start your first exam to see results here.</td>
                </tr>
              <%
                }
              %>
            </table>
          </div>
        </section>
      </section>
    </main>

    <script src="js/theme.js"></script>
  </body>
</html>
