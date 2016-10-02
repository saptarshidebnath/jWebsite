package me.saptarshidebnath.jwebsite.db;

import me.saptarshidebnath.jwebsite.utils.Utils;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static me.saptarshidebnath.jwebsite.utils.Cnst.ENV_DATABASE_URL_HEROKU_TOEKNEIZER;
import static me.saptarshidebnath.jwebsite.utils.Cnst.JPA_DB_CONF_NAME;

/** Created by saptarshi on 9/11/2016. */
public class JwDbEntityManager {
  private static JwDbEntityManager instance = null;
  private EntityManagerFactory emf = null;

  private JwDbEntityManager() {
    JLog.info("Initiating Database connection");
    try {
      this.emf =
          Persistence.createEntityManagerFactory(
              JPA_DB_CONF_NAME,
              Utils.getHerokuPostgresDBDetails("postgresql", ENV_DATABASE_URL_HEROKU_TOEKNEIZER));
    } catch (final NoSuchAlgorithmException e) {
      JLog.severe("Unable to create Database", e);
      JLog.severe("Exiting application");
      this.emf = null;
    }
    if (this.emf != null) {
      JLog.info("Database connection established");
    }
  }

  public static JwDbEntityManager getInstance() {
    if (JwDbEntityManager.instance == null) {
      synchronized (JwDbEntityManager.class) {
        if (JwDbEntityManager.instance == null) {
          instance = new JwDbEntityManager();
        }
      }
    }
    return JwDbEntityManager.instance;
  }

  public void persist(final List<Object> entityList) {
    this.persist(entityList.toArray());
  }

  public void persist(final Object... entities) {
    final EntityManager em = this.emf.createEntityManager();
    em.getTransaction().begin();
    Arrays.stream(entities).forEach(em::persist);
    em.getTransaction().commit();
    em.close();
  }

  public EntityManagerFactory getEntityManagerFactory() {
    return this.emf;
  }
}
