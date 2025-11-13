<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--
  Logout helper: invalidates the current session and redirects back to the home page.
  Both students and teachers can use this link for a quick, clean sign out.
-->
<%
  if (session != null) {
      session.invalidate();
  }
  response.sendRedirect(request.getContextPath() + "/index.jsp");
%>

