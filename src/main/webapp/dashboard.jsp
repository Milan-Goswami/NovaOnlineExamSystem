<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Beginner note: This legacy file now redirects to the enhanced student_dashboard.jsp page. -->
<%
  response.sendRedirect(request.getContextPath() + "/student_dashboard.jsp");
%>
