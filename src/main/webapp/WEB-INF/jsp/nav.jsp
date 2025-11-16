<%@ page import="com.nova.servlet.ServletHelpers" %>
<!-- Updated to keep the navbar compact with only brand, theme toggle, and sign-out link. -->
<%
  String contextPath = request.getContextPath();
  boolean studentLoggedIn = ServletHelpers.isStudentLoggedIn(request);
  boolean teacherLoggedIn = ServletHelpers.isTeacherLoggedIn(request);
  String brandHref = contextPath + "/index.jsp";
  String logoutHref;
  if (teacherLoggedIn) {
      logoutHref = contextPath + "/teacher-logout";
  } else {
      // Fallback logout endpoint for students or guests; replace if a student-specific servlet is added.
      logoutHref = contextPath + "/logout.jsp";
  }
%>
<header class="site-nav site-nav--compact">
  <a class="site-nav__title">Nova Online Exam</a>
  <div class="site-nav__actions">
    <button class="btn btn-secondary btn--sm" type="button" data-theme-toggle>Toggle theme</button>
    <a class="btn btn-outline btn--sm" href="<%= logoutHref %>">Sign out</a>
  </div>
</header>

