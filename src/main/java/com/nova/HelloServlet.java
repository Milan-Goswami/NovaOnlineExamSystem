package com.nova;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A minimal test servlet to verify Tomcat 10 (Jakarta Servlet 5.0) compatibility.
 *
 * URL mapping is defined in web.xml as /hello
 */
public class HelloServlet extends HttpServlet {

    /**
     * Handles HTTP GET requests and responds with a simple HTML message.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("  <meta charset=\"UTF-8\"/>");
            out.println("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>");
            out.println("  <title>Hello from HelloServlet</title>");
            out.println("  <style>body{font-family:Arial,Helvetica,sans-serif;margin:2rem} .ok{color:#0a7f2e}</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("  <h1 class=\"ok\">Hello from NovaOnlineExamSystem!</h1>");
            out.println("  <p>This is a test response from <strong>com.nova.HelloServlet</strong>.</p>");
            out.println("  <p>Try the index page at <a href=\"/NovaOnlineExamSystem/\">/NovaOnlineExamSystem/</a>.</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}


