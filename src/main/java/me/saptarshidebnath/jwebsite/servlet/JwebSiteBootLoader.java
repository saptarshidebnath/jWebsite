package me.saptarshidebnath.jwebsite.servlet;

import me.saptarshidebnath.jwebsite.db.entity.website.Config;
import me.saptarshidebnath.jwebsite.utils.Utils;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.security.NoSuchAlgorithmException;

import static me.saptarshidebnath.jwebsite.utils.Constants.ENV_DATABASE_URL_HEROKU_TOEKNEIZER;

@WebListener
public class JwebSiteBootLoader implements ServletContextListener {

  @Override
  public void contextDestroyed(final ServletContextEvent event) {
    JLog.info("Context to be destroyed. " + event.toString());
  }

  @Override
  public void contextInitialized(final ServletContextEvent event) {
    JLog.info("Jwebsite instance booting up.");
    JLog.info("Setting up data base connection");
    final EntityManagerFactory entityManagerFactory;

    try {
      entityManagerFactory =
          Persistence.createEntityManagerFactory(
              "JPATest",
              Utils.getHerokuPostgresDBDetails("postgresql", ENV_DATABASE_URL_HEROKU_TOEKNEIZER));

      final EntityManager em = entityManagerFactory.createEntityManager();
      // Create new user
      em.getTransaction().begin();
      final Config user = new Config();
      user.setConfigName("saptarshi" + System.currentTimeMillis());
      user.setConfigValue("debnath");
      em.persist(user);
      em.getTransaction().commit();

      em.close();

    } catch (final NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    JLog.info("Data base connectivity complete.");
    JLog.info("JWebsite boot loading complete");
  }
}
