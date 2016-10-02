package me.saptarshidebnath.jwebsite.servlet;

import me.saptarshidebnath.jwebsite.db.JwDbEntityManager;
import me.saptarshidebnath.jwebsite.db.entity.page.HtmlContent;
import me.saptarshidebnath.jwebsite.db.entity.page.WebPage;
import me.saptarshidebnath.jwebsite.db.entity.page.metainfo.MetaInfo;
import me.saptarshidebnath.jwebsite.db.entity.website.JwConfig;
import me.saptarshidebnath.jwebsite.utils.Cnst;
import me.saptarshidebnath.jwebsite.utils.Utils;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;

@WebListener
public class JwebSiteBootLoader implements ServletContextListener {

  @Override
  public void contextDestroyed(final ServletContextEvent event) {
    JLog.info("Context to be destroyed. " + event.toString());
  }

  @Override
  public void contextInitialized(final ServletContextEvent event) {
    JLog.info("Jwebsite instance booting up.");

    final JwConfig user =
        new JwConfig()
            .setConfigName(Cnst.DB_CONFIG_KEY_JSP_LOCATION)
            .setConfigValue(Cnst.DB_CONFIG_VALUE_JSP_LOCATION);

    final WebPage wp =
        new WebPage()
            .setTitle("Admin Page")
            .setUrlPath("/jwebsite/admin")
            .setMetaInfoList(
                Utils.getAsArrayList(
                    new MetaInfo().setName("foo1").setContent("bar1"),
                    new MetaInfo().setName("foo2").setContent("bar2")))
            .setHtmlContentList(
                Utils.getAsArrayList(
                    new HtmlContent()
                        .setHtmlContent("<html><body><h1>Admin Page</h1></body></html>")
                        .setCreateTime(new Date())));

    JwDbEntityManager.getInstance().persist(user, wp);

    JLog.info("JWebsite boot loading complete.");
  }
}
