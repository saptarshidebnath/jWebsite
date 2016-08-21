package me.saptarshidebnath.jwebsite.servlet;

import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 The Controller servlet for jWebsite.
 The Servlet handles all the {@link javax.servlet.http.HttpServletRequest HttpServletRequest}
 and redirects all the users to the correct handlers.
 */
@WebServlet(value = "/*",
            name = "URL handler servlet")
public class UrlHandler extends HttpServlet {

  /**
   The following method handles any GET request to the web application

   @param request
   Takes a reference of {@link javax.servlet.http.HttpServletRequest HttpServletRequest} this is
   sent by the web server created from the http request sent by the HttpClient [generally a
   browser like Chrome]
   @param response
   Takes a reference {@link javax.servlet.http.HttpServletResponse}  which is going to be
   populated and sent to the HttpClient [Generally a browser like Chrome]

   @throws ServletException
   Throws {@link javax.servlet.ServletException ServletException} to report any Servlet Error
   @throws IOException
   Throws {@link java.io.IOException IOException} to report any IO Exception
   @see javax.servlet.http.HttpServlet
   @see javax.servlet.http.HttpServletRequest
   @see javax.servlet.http.HttpServletResponse
   */
  @Override
  public void doGet(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {
    // Set response content type
    response.setContentType("text/html");

    // Actual logic goes here.
    response.getWriter()
            .println("<h1>Default Servlet/h1>");
  }

  /**
   Method is called when the current servlet is destroyed.
   */
  @Override
  public void destroy() {
    JLog.info("Bye Bye !!");
  }

  /**
   The init method is called once before serving the first request. This method is used to
   initiate resources for the servlet.
   */
  @Override
  public void init() {
    JLog.info("Initiated !!");
  }
}
