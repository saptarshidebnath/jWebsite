package me.saptarshidebnath.jwebsite.servlet;

import me.saptarshidebnath.jwebsite.db.JwDbEntityManager;
import me.saptarshidebnath.jwebsite.db.entity.page.WebPage;
import me.saptarshidebnath.jwebsite.db.entity.website.Config;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class JwebSiteBootLoader implements ServletContextListener {

  @Override
  public void contextDestroyed(final ServletContextEvent event) {
    JLog.info("Context to be destroyed. " + event.toString());
  }

  @Override
  public void contextInitialized(final ServletContextEvent event) {
    JLog.info("Jwebsite instance booting up.");

    final Config user = new Config();
    user.setConfigName("saptarshi" + System.currentTimeMillis());
    user.setConfigValue("debnath");

    final WebPage wp = new WebPage();
    wp.setJspFileName("sap.jsp");
    wp.setTitle("title");
    wp.setUri("/sap/dev");

    JwDbEntityManager.getInstance().persist(user, wp);

    JLog.info("JWebsite boot loading complete");
  }
}
