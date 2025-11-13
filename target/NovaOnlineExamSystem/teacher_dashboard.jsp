<%@ page import="java.util.List" %>
<%@ page import="com.nova.model.Question" %>
<%@ page import="com.nova.servlet.ServletHelpers" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--
  Teacher dashboard: offers a wrapped table with edit/delete controls that stay usable on mobile.
  Summary cards highlight key stats, and long text wraps without forcing horizontal scroll.
-->
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Teacher Dashboard • Nova Online Examination System</title>
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

    String teacherName = "Teacher";
    String teacherEmail = null;
    if (session != null) {
        if (session.getAttribute("teacherName") != null) {
            teacherName = session.getAttribute("teacherName").toString();
        }
        if (session.getAttribute("teacherEmail") != null) {
            teacherEmail = session.getAttribute("teacherEmail").toString();
        }
    }

    @SuppressWarnings("unchecked")
    List<Question> questions = (List<Question>) request.getAttribute("questions");
    if (questions == null) {
        questions = java.util.Collections.emptyList();
    }
    int questionCount = questions.size();

    String status = request.getParameter("status");
    String message = null;
    String messageCss = "";
    String messageIcon = "ℹ️";
    if ("added".equalsIgnoreCase(status)) {
        message = "Question added successfully!";
        messageCss = "alert-box--success";
        messageIcon = "✅";
    } else if ("updated".equalsIgnoreCase(status)) {
        message = "Question updated successfully!";
        messageCss = "alert-box--info";
        messageIcon = "✨";
    } else if ("deleted".equalsIgnoreCase(status)) {
        message = "Question deleted successfully!";
        messageCss = "alert-box--warning";
        messageIcon = "⚠️";
    } else if ("error".equalsIgnoreCase(status)) {
        message = "Something went wrong. Please try again.";
        messageCss = "alert-box--danger";
        messageIcon = "⛔";
    }
  %>
    <div class="page-shell">
      <%@ include file="/WEB-INF/jsp/nav.jsp" %>

      <main class="page-content">
        <section class="dashboard-layout">
          <section class="card">
            <header class="dashboard-header">
              <div>
                <h1>Welcome, <%= teacherName %></h1>
                <p class="supporting-text">
                  Review, edit, and delete questions without worrying about layout glitches. Every column wraps automatically.
                </p>
              </div>
              <a class="btn btn-primary" href="${pageContext.request.contextPath}/add_question.jsp">Add Question</a>
            </header>

            <% if (message != null) { %>
              <div class="alert-box <%= messageCss %>">
                <span class="alert-icon"><%= messageIcon %></span>
                <span><%= message %></span>
              </div>
            <% } %>

            <div class="summary-grid">
              <article class="summary-card">
                <p class="summary-card__label">Total Questions</p>
                <p class="summary-card__value"><%= questionCount %></p>
              </article>
              <article class="summary-card">
                <p class="summary-card__label">Latest Update</p>
                <p class="summary-card__value"><%= message != null ? message : "No changes yet" %></p>
              </article>
              <article class="summary-card">
                <p class="summary-card__label">Signed In As</p>
                <p class="summary-card__value"><%= teacherEmail != null ? teacherEmail : "teacher" %></p>
              </article>
            </div>

            <div class="table-wrapper table-wrapper--elevated">
              <table class="data-table">
                <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Question Text</th>
                    <th scope="col">Option A</th>
                    <th scope="col">Option B</th>
                    <th scope="col">Option C</th>
                    <th scope="col">Option D</th>
                    <th scope="col">Correct</th>
                    <th scope="col">Actions</th>
                  </tr>
                </thead>
                <tbody>
                <% if (!questions.isEmpty()) {
                     for (Question q : questions) { %>
                  <tr>
                    <td data-label="ID"><%= q.getId() %></td>
                    <td data-label="Question" class="cell-question"><%= q.getQuestionText() %></td>
                    <td data-label="Option A"><%= q.getOptionA() %></td>
                    <td data-label="Option B"><%= q.getOptionB() %></td>
                    <td data-label="Option C"><%= q.getOptionC() %></td>
                    <td data-label="Option D"><%= q.getOptionD() %></td>
                    <td data-label="Correct"><span class="badge badge-success">Option <%= q.getCorrectOption() %></span></td>
                    <td data-label="Actions">
                      <div class="table-actions">
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/update_question.jsp?id=<%= q.getId() %>">Edit</a>
                        <a class="btn btn-danger delete-link" href="${pageContext.request.contextPath}/delete-question?id=<%= q.getId() %>">Delete</a>
                      </div>
                    </td>
                  </tr>
                <%   }
                   } else { %>
                  <tr>
                    <td data-label="Notice" colspan="8">No questions yet. Use “Add Question” to create the first one.</td>
                  </tr>
                <% } %>
                </tbody>
              </table>
            </div>
          </section>
        </section>
      </main>

      <footer class="site-footer">
        <span>Nova Online Exam System – MCA Project</span>
      </footer>

      <script src="${pageContext.request.contextPath}/js/theme.js"></script>
      <script>
        document.addEventListener('DOMContentLoaded', function () {
          var deleteLinks = document.querySelectorAll('.delete-link');
          deleteLinks.forEach(function (link) {
            link.addEventListener('click', function (event) {
              var row = link.closest('tr');
              var questionCell = row ? row.querySelector('.cell-question') : null;
              var questionText = questionCell ? questionCell.textContent.trim() : 'this question';
              var confirmed = window.confirm('Are you sure you want to delete "' + questionText + '"? This action cannot be undone.');
              if (!confirmed) {
                event.preventDefault();
              }
            });
          });
        });
      </script>
    </div>
  </body>
</html>
