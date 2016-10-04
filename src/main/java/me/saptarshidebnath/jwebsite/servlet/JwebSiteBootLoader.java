package me.saptarshidebnath.jwebsite.servlet;

import me.saptarshidebnath.jwebsite.cms.JwCms;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class JwebSiteBootLoader implements ServletContextListener {

  @Override
  public void contextDestroyed(final ServletContextEvent event) {
    JLog.info("Context to be destroyed. " + event.toString());
  }

  @Override
  public void contextInitialized(final ServletContextEvent event) {
    JLog.info("Jwebsite instance booting up.");

    try {
      JwCms.getInstance().initJwCms(event.getServletContext().getRealPath("/"));
    } catch (final IOException e) {
      JLog.severe("Error Received", e);
    }

    JLog.info("JWebsite boot loading complete.");
  }
}
