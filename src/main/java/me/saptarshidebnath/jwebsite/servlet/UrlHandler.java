package me.saptarshidebnath.jwebsite.servlet;

import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

/**
 * Created by Saptarshi on 8/15/2016.
 */

@WebServlet(value = "/*", name = "URL handler servlet")
public class UrlHandler extends HttpServlet {

    private String message = "Default Servlet";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("me.saptarshidebnath.jwebsite.utils.jlog.JLog");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        JLog.severe("Initiated !!");
        JLog.log(Level.INFO, "New Message");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
    }

    @Override
    public void destroy() {
        JLog.info("Bye Bye !!");
    }
}
