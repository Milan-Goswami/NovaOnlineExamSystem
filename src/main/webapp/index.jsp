<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.nova.servlet.ServletHelpers" %>
<!-- Beginner note: This landing page is a JSP so we can reuse the shared navigation include. -->
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Nova Online Examination System</title>
    <link rel="stylesheet" href="css/style.css" />
  </head>
  <body class="landing-page">
    <%@ include file="/WEB-INF/jsp/nav.jsp" %>

    <main class="page-content">
      <section class="landing-header">
        <h1 class="landing-title">Take Exams with Confidence</h1>
        <p class="landing-tagline">
          Nova Online Examination System creates a calm, consistent experience for busy students and teachers.
        </p>
      </section>

      <section class="landing-action">
        <a class="portal-card portal-card--student" href="student_login.html">
          <span aria-hidden="true" class="portal-icon">🎓</span>
          <div>
            <h2>I am a Student</h2>
            <p>Log in to start exams, review answers, and track progress.</p>
          </div>
        </a>

        <a class="portal-card portal-card--teacher" href="teacher_login.html">
          <span aria-hidden="true" class="portal-icon">🧑‍🏫</span>
          <div>
            <h2>I am a Teacher</h2>
            <p>Access question banks, manage tests, and monitor results.</p>
          </div>
        </a>
      </section>

      <section class="landing-links">
        <a class="text-link" href="student_register.html">Create a student account</a>
        <a class="text-link" href="teacher_register.html">First time teacher? Register here</a>
      </section>
    </main>

    <footer class="site-footer">
      <span>Nova Online Exam System – MCA Project</span>
    </footer>

    <script src="js/theme.js"></script>
  </body>
</html>
