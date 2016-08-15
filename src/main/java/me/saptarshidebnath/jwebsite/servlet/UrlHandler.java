package me.saptarshidebnath.jwebsite.servlet;

import me.saptarshidebnath.jwebsite.utils.JLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Saptarshi on 8/15/2016.
 */

@WebServlet(value="/", name="URL handler servlet")
public class UrlHandler extends HttpServlet {

    private String message;

    public void init() throws ServletException {
        JLog.info("Initiated !!");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
    }

    public void destroy() {
        JLog.info("Bye Bye !!");
    }
}
