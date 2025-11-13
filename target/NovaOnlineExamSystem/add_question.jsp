<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.nova.servlet.ServletHelpers" %>
<!DOCTYPE html>
<!--
  Add question page: supplies a widescreen card with generous spacing and responsive inputs.
  Teachers can add new questions without layout issues on phones or laptops.
-->
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Add Question • Nova Online Examination System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  </head>
  <body class="dashboard-page">
  <%
    if (!ServletHelpers.isTeacherLoggedIn(request)) {
        response.sendRedirect(request.getContextPath() + "/teacher_login.html?error=unauthorized");
        return;
    }
    if (ServletHelpers.isStudentLoggedIn(request)) {
        response.sendRedirect(request.getContextPath() + "/student_dashboard.jsp");
        return;
    }
  %>
    <%@ include file="/WEB-INF/jsp/nav.jsp" %>

    <main class="page-content">
      <section class="dashboard-layout">
        <section class="card">
          <h1>Add New Question</h1>
          <p class="supporting-text">
            Complete every field below. Long text wraps naturally, and form controls stretch edge-to-edge for easier typing.
          </p>

          <form class="form-grid" method="post" action="${pageContext.request.contextPath}/add-question">
            <div class="form-field">
              <label for="questionText">Question Text</label>
              <textarea id="questionText" name="questionText" rows="4" placeholder="Type the full question here" required></textarea>
            </div>
            <div class="form-field">
              <label for="optionA">Option A</label>
              <input id="optionA" name="optionA" type="text" placeholder="First option" required />
            </div>
            <div class="form-field">
              <label for="optionB">Option B</label>
              <input id="optionB" name="optionB" type="text" placeholder="Second option" required />
            </div>
            <div class="form-field">
              <label for="optionC">Option C</label>
              <input id="optionC" name="optionC" type="text" placeholder="Third option" required />
            </div>
            <div class="form-field">
              <label for="optionD">Option D</label>
              <input id="optionD" name="optionD" type="text" placeholder="Fourth option" required />
            </div>
            <div class="form-field">
              <label for="correctOption">Correct Option</label>
              <select id="correctOption" name="correctOption" required>
                <option value="">Select the correct option</option>
                <option value="A">A</option>
                <option value="B">B</option>
                <option value="C">C</option>
                <option value="D">D</option>
              </select>
            </div>
            <div class="actions">
              <button type="submit" class="btn btn-primary">Save Question</button>
              <a class="btn btn-secondary" href="${pageContext.request.contextPath}/view-questions">Back to Dashboard</a>
            </div>
          </form>
        </section>
      </section>
    </main>

    <footer class="site-footer">
      <span>Nova Online Exam System – MCA Project</span>
    </footer>

    <script src="${pageContext.request.contextPath}/js/theme.js"></script>
  </body>
</html>

