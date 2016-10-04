package me.saptarshidebnath.jwebsite.db;

import me.saptarshidebnath.jwebsite.utils.Utils;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static me.saptarshidebnath.jwebsite.utils.Cnst.ENV_DATABASE_URL_HEROKU_TOEKNEIZER;
import static me.saptarshidebnath.jwebsite.utils.Cnst.JPA_DB_CONF_NAME;

/** Created by saptarshi on 9/11/2016. */
public class JwDbEntityManager {
  private static JwDbEntityManager instance = null;
  private EntityManagerFactory entityManagerFactory = null;
  private JinqJPAStreamProvider streams;

  private JwDbEntityManager() {
    JLog.info("Initiating Database connection");
    try {
      this.entityManagerFactory =
          Persistence.createEntityManagerFactory(
              JPA_DB_CONF_NAME,
              Utils.getHerokuPostgresDBDetails("postgresql", ENV_DATABASE_URL_HEROKU_TOEKNEIZER));
      this.streams = new JinqJPAStreamProvider(this.entityManagerFactory);
    } catch (final NoSuchAlgorithmException e) {
      JLog.severe("Unable to create Database", e);
      JLog.severe("Exiting application");
      this.entityManagerFactory = null;
    }
    if (this.entityManagerFactory != null) {
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
    final EntityManager em = this.getEntityManager();
    final EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    Arrays.stream(entities).forEach(em::persist);
    transaction.commit();
    em.close();
  }

  public <T> JinqStream<T> streamData(final Class<T> clazz) {
    return this.streams.streamAll(this.getEntityManager(), clazz);
  }

  private EntityManager getEntityManager() {
    return this.entityManagerFactory.createEntityManager();
  }

  public void merge(final Object... entities) {
    final EntityManager em = this.getEntityManager();
    em.getTransaction().begin();
    Arrays.stream(entities).forEach(em::merge);
    em.getTransaction().commit();
    em.close();
  }
}
