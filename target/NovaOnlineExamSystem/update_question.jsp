<%@ page import="com.nova.dao.QuestionDAO" %>
<%@ page import="com.nova.model.Question" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--
  Edit question page: loads the selected row and keeps the update form tidy even on mobile devices.
  Teachers can change every field without dealing with cramped layouts or horizontal scroll.
-->
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Edit Question • Nova Online Examination System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  </head>
  <body class="dashboard-page">
    <%
      Integer teacherId = (session != null) ? (Integer) session.getAttribute("teacherId") : null;
      Integer studentId = (session != null) ? (Integer) session.getAttribute("studentId") : null;
      if (teacherId == null) {
          response.sendRedirect(request.getContextPath() + "/teacher_login.html?error=unauthorized");
          return;
      }
      if (studentId != null) {
          response.sendRedirect(request.getContextPath() + "/student_dashboard.jsp");
          return;
      }

      String idParam = request.getParameter("id");
      Question question = null;
      if (idParam != null) {
          try {
              int questionId = Integer.parseInt(idParam);
              question = new QuestionDAO().getQuestionById(questionId);
          } catch (NumberFormatException ignore) {
              question = null;
          }
      }
    %>

    <%@ include file="/WEB-INF/jsp/nav.jsp" %>

    <main class="page-content">
      <section class="dashboard-layout">
        <section class="card">
          <h1>Edit Question</h1>
          <%
            if (question == null) {
          %>
            <p class="supporting-text text-danger">
              Question not found. Please return to the dashboard and choose a valid question.
            </p>
            <div class="actions">
              <a class="btn btn-secondary" href="${pageContext.request.contextPath}/view-questions">Back to Dashboard</a>
            </div>
          <%
            } else {
          %>
          <p class="supporting-text">
            Update the fields below and click save. Each input stretches across the card so you can review longer text easily.
          </p>

          <form class="form-grid" method="post" action="${pageContext.request.contextPath}/update-question">
            <input type="hidden" name="id" value="<%= question.getId() %>" />

            <div class="form-field">
              <label for="questionText">Question Text</label>
              <textarea id="questionText" name="questionText" rows="4" required><%= question.getQuestionText() %></textarea>
            </div>
            <div class="form-field">
              <label for="optionA">Option A</label>
              <input id="optionA" name="optionA" type="text" value="<%= question.getOptionA() %>" required />
            </div>
            <div class="form-field">
              <label for="optionB">Option B</label>
              <input id="optionB" name="optionB" type="text" value="<%= question.getOptionB() %>" required />
            </div>
            <div class="form-field">
              <label for="optionC">Option C</label>
              <input id="optionC" name="optionC" type="text" value="<%= question.getOptionC() %>" required />
            </div>
            <div class="form-field">
              <label for="optionD">Option D</label>
              <input id="optionD" name="optionD" type="text" value="<%= question.getOptionD() %>" required />
            </div>
            <div class="form-field">
              <label for="correctOption">Correct Option</label>
              <select id="correctOption" name="correctOption" required>
                <option value="A" <%= "A".equalsIgnoreCase(question.getCorrectOption()) ? "selected" : "" %>>A</option>
                <option value="B" <%= "B".equalsIgnoreCase(question.getCorrectOption()) ? "selected" : "" %>>B</option>
                <option value="C" <%= "C".equalsIgnoreCase(question.getCorrectOption()) ? "selected" : "" %>>C</option>
                <option value="D" <%= "D".equalsIgnoreCase(question.getCorrectOption()) ? "selected" : "" %>>D</option>
              </select>
            </div>

            <div class="actions">
              <button type="submit" class="btn btn-primary">Update Question</button>
              <a class="btn btn-secondary" href="${pageContext.request.contextPath}/view-questions">Cancel</a>
            </div>
          </form>
          <%
            }
          %>
        </section>
      </section>
    </main>

    <footer class="site-footer">
      <span>Nova Online Exam System – MCA Project</span>
    </footer>

    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
  </body>
</html>

