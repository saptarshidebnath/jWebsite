package me.saptarshidebnath.jwebsite.cms;

import me.saptarshidebnath.jwebsite.db.JwDbEntityManager;
import me.saptarshidebnath.jwebsite.db.entity.page.HtmlContent;
import me.saptarshidebnath.jwebsite.db.entity.page.MetaInfo;
import me.saptarshidebnath.jwebsite.db.entity.page.WebPage;
import me.saptarshidebnath.jwebsite.db.entity.website.JwConfig;
import me.saptarshidebnath.jwebsite.utils.Cnst;
import me.saptarshidebnath.jwebsite.utils.Utils;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static me.saptarshidebnath.jwebsite.utils.Cnst.DB_CONFIG_KEY_WEB_APP_ROOT_REAL_PATH;
import static me.saptarshidebnath.jwebsite.utils.Cnst.JW_JSP_EXTENSION;
import static me.saptarshidebnath.jwebsite.utils.Cnst.JW_JSP_PREFIX;
import static me.saptarshidebnath.jwebsite.utils.Utils.singletonCollector;

public class JwCms {
  private static JwCms instance;

  private final JwConfig jwConfig;
  private final ArrayList<WebPage> webPages;
  private final Boolean initialWebPagesCreated = false;
  private Boolean dbInitiated = false;
  private String rootRealPath = null;

  {
    this.jwConfig =
        new JwConfig()
            .setConfigName(Cnst.DB_CONFIG_KEY_JSP_LOCATION)
            .setConfigValue(Cnst.DB_CONFIG_VALUE_JSP_LOCATION);

    this.webPages =
        Utils.getAsArrayList(
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
                            .setCreateTime(new Date()))));
  }

  public static JwCms getInstance() {
    if (JwCms.instance == null) {
      synchronized (JwCms.class) {
        if (JwCms.instance == null) {
          instance = new JwCms();
        }
      }
    }
    return JwCms.instance;
  }

  public String getJspDumpLocation() {

    return JwDbEntityManager.getInstance()
            .streamData(JwConfig.class)
            .filter(
                e -> e.getConfigName().equalsIgnoreCase(Cnst.DB_CONFIG_KEY_WEB_APP_ROOT_REAL_PATH))
            .collect(singletonCollector())
            .getConfigValue()
        + File.separator
        + JwDbEntityManager.getInstance()
            .streamData(JwConfig.class)
            .filter(e -> e.getConfigName().equalsIgnoreCase(Cnst.DB_CONFIG_KEY_JSP_LOCATION))
            .collect(singletonCollector())
            .getConfigValue();
  }

  public void publishJsp(final String urlPath) throws IOException {
    final WebPage webPage =
        JwDbEntityManager.getInstance()
            .streamData(WebPage.class)
            .filter(e -> e.getUrlPath().equalsIgnoreCase(urlPath))
            .collect(singletonCollector());
    JLog.info(webPage.toString());
    if (webPage.getJspFileName() == null || webPage.getJspFileName().length() == 0) {
      final File fileDirectory = new File(this.getJspDumpLocation());
      fileDirectory.mkdirs();
      JLog.info("Target JSP directory : " + fileDirectory.getCanonicalPath());
      if (fileDirectory.exists()) {
        final File temporaryJspFile =
            File.createTempFile(JW_JSP_PREFIX, JW_JSP_EXTENSION, fileDirectory);
        final String htmlContent = webPage.getHtmlContentList().get(0).getHtmlContent();
        Utils.writeFile(temporaryJspFile, htmlContent);
        JLog.info("Created JSP file : " + temporaryJspFile.getCanonicalPath());
        webPage.setJspFileName(FilenameUtils.getName(temporaryJspFile.getCanonicalPath()));
        JwDbEntityManager.getInstance().merge(webPage);
      } else {
        throw new IOException("Unable to create directory : " + fileDirectory.getCanonicalPath());
      }
    }
  }

  public void initateJwCms(final String realPath) throws IOException {
    this.rootRealPath = realPath;
    this.createInitialDbEntries(realPath);
    this.createInitialWebPages();
  }

  private void createInitialDbEntries(final String realPath) {
    synchronized (this.dbInitiated) {
      if (this.dbInitiated == false) {
        JLog.info("Pre populating database");
        final ArrayList<Object> listOfObjectToPersist = new ArrayList<>();

        //
        // Store the JSP dump location
        //
        listOfObjectToPersist.add(this.jwConfig);

        //
        // Store the web app root real path
        //
        listOfObjectToPersist.add(
            new JwConfig()
                .setConfigName(DB_CONFIG_KEY_WEB_APP_ROOT_REAL_PATH)
                .setConfigValue(realPath));

        //
        // Store web pages
        //
        listOfObjectToPersist.addAll(this.webPages);
        JwDbEntityManager.getInstance().persist(listOfObjectToPersist.toArray());
        JLog.info("Data base pre population complete");
        this.dbInitiated = true;
      }
    }
  }

  private void createInitialWebPages() throws IOException {
    synchronized (this.initialWebPagesCreated) {
      if (this.initialWebPagesCreated == false) {
        final List<String> urlList =
            this.webPages.stream().map(e -> e.getUrlPath()).collect(Collectors.toList());
        for (final String url : urlList) {
          this.publishJsp(url);
        }
      }
    }
  }
}
