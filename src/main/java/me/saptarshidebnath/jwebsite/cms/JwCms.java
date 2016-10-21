package me.saptarshidebnath.jwebsite.cms;

import me.saptarshidebnath.jwebsite.db.JwDbEntityManager;
import me.saptarshidebnath.jwebsite.db.entity.page.HtmlContent;
import me.saptarshidebnath.jwebsite.db.entity.page.MetaInfo;
import me.saptarshidebnath.jwebsite.db.entity.page.WebPage;
import me.saptarshidebnath.jwebsite.db.entity.website.JwConfig;
import me.saptarshidebnath.jwebsite.db.status.PageContent;
import me.saptarshidebnath.jwebsite.node.NpmPackageInstaller;
import me.saptarshidebnath.jwebsite.utils.Cnst;
import me.saptarshidebnath.jwebsite.utils.Utils;
import me.saptarshidebnath.jwebsite.utils.WebInstanceConstants;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static me.saptarshidebnath.jwebsite.utils.Cnst.*;
import static me.saptarshidebnath.jwebsite.utils.Utils.singletonCollector;

public class JwCms {
  private static JwCms instance;
  private long lastWebPageListingTime;
  /**
   * This is the current web pages cache. Please use {@link JwCms#getAllWebPages} to get the current
   * web page cache
   */
  private List<WebPage> currentWebPageCache;

  private List<WebPage> lightWeightWebPageUniverse;

  {
    this.lastWebPageListingTime = -1;
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

  private void publishAllWebPages() throws IOException {

    //
    // Check and delete JSP dump location
    //
    final File jspDumpLocation =
        new File(WebInstanceConstants.INST.getValueFor(WIC_KEY_JSP_DUMP_PATH));
    if (jspDumpLocation.exists()) {
      if (jspDumpLocation.delete()) {
        JLog.info("JSP Dump directory deleted : " + jspDumpLocation.getCanonicalPath());
      } else {
        final String message =
            "Unable to delete JSP dump directory : " + jspDumpLocation.getCanonicalPath();
        JLog.severe(message);
        throw new IOException(message);
      }
    }
    final List<WebPage> webPageList = this.getAllWebPages();
    //
    // Create webpages one by one
    //
    for (final WebPage currentWebPage : webPageList) {
      this.publishJsp(currentWebPage);
    }
  }

  public void publishJsp(final String urlPath) throws IOException {
    this.publishJsp(
        JwDbEntityManager.getInstance()
            .streamData(WebPage.class)
            .filter(e -> e.getUrlPath().equalsIgnoreCase(urlPath))
            .collect(singletonCollector()));
  }

  public void publishJsp(final WebPage webPage) throws IOException {

    JLog.info(webPage.toString());

    final File fileDirectory =
        new File(WebInstanceConstants.INST.getValueFor(WIC_KEY_JSP_DUMP_PATH));
    fileDirectory.mkdirs();

    JLog.info("Target JSP directory : " + fileDirectory.getCanonicalPath());
    if (fileDirectory.exists()) {
      //
      // Delete JSP files which were created previously if any
      //
      final File prevJspFile =
          new File(
              WebInstanceConstants.INST.getValueFor(WIC_KEY_JSP_DUMP_PATH)
                  + File.separator
                  + webPage.getJspFileName());
      if (webPage.getJspFileName() != null
          && webPage.getJspFileName().length() > 0
          && prevJspFile.exists()) {
        final boolean fileDeleteStatus = prevJspFile.delete();
        //
        // Warn in the log that the file was not deleted successfully
        //
        if (fileDeleteStatus == false) {
          JLog.warning(
              "Unable to delete previously created JSP file. Please delete manually : "
                  + prevJspFile.getCanonicalPath());
        }
      }
      final File temporaryJspFile =
          File.createTempFile(JW_JSP_PREFIX, JW_JSP_EXTENSION, fileDirectory);
      final HtmlContent latestHtmlContent = webPage.getHtmlContentList().get(0);
      final String htmlContent = latestHtmlContent.getHtmlContent();
      Utils.writeFile(temporaryJspFile, htmlContent);
      latestHtmlContent.setStatus(PageContent.PUBLISHED);
      JLog.info("Created JSP file : " + temporaryJspFile.getCanonicalPath());
      webPage.setJspFileName(FilenameUtils.getName(temporaryJspFile.getCanonicalPath()));
      JwDbEntityManager.getInstance().merge(webPage, latestHtmlContent);
    } else {
      throw new IOException("Unable to create directory : " + fileDirectory.getCanonicalPath());
    }
  }

  public void initJwCms(final String realPath)
      throws IOException, NoSuchAlgorithmException, InterruptedException {
    JLog.info("JwCMS Initiating..");
    //
    // The order is important
    //

    //
    //Create DB Records
    //
    this.createInitialDbRecords();

    //
    //Create WebInstance Cache
    //
    this.createWICEntries(realPath);

    //
    //Publish all jsp files
    //
    this.publishAllWebPages();

    //
    // Install NPM dependencies
    //
    NpmPackageInstaller.getInstance().installAllPackagesMarkedInDataBase();
  }

  private void createWICEntries(final String realPath)
      throws IOException, NoSuchAlgorithmException {
    JLog.info("Creating Web Instance Cache");
    //
    // Store the web app root real path
    //
    WebInstanceConstants.INST.storeValue(WIC_KEY_ROOT_REAL_PATH, realPath);

    //
    // Store JSP dump location real path
    //

    final String jspDumpLocation =
        realPath
            + File.separator
            + JwDbEntityManager.getInstance()
                .streamData(JwConfig.class)
                .filter(e -> e.getConfigName().equalsIgnoreCase(Cnst.DB_CONFIG_KEY_JSP_LOCATION))
                .collect(singletonCollector())
                .getConfigValue();

    WebInstanceConstants.INST.storeValue(WIC_KEY_JSP_DUMP_PATH, jspDumpLocation);

    WebInstanceConstants.INST.storeValue(
        WIC_KEY_NODE_INST_DIR, realPath + "WEB-INF" + File.separator + "npm-inst");
    JLog.info("Web Instance cache creation compete");
  }

  private void createInitialDbRecords() throws IOException {
    JLog.info("Pre populating database");

    final ArrayList<Object> listOfObjectToPersist = new ArrayList<>();

    //
    // Store the JSP dump location
    //
    listOfObjectToPersist.add(
        new JwConfig()
            .setConfigName(Cnst.DB_CONFIG_KEY_JSP_LOCATION)
            .setConfigValue(Cnst.DB_CONFIG_VALUE_JSP_LOCATION));

    //
    // Store web pages
    //
    listOfObjectToPersist.addAll(
        Utils.getAsArrayList(
            new WebPage()
                .setTitle("Admin Page")
                .setUrlPath(DB_WEBPAGE_ADMIN_PG_UI)
                .setMetaInfoList(
                    Utils.getAsArrayList(
                        new MetaInfo().setName("foo1").setContent("bar1"),
                        new MetaInfo().setName("foo2").setContent("bar2")))
                .setHtmlContentList(
                    Utils.getAsArrayList(
                        new HtmlContent()
                            .setStatus(PageContent.SAVED)
                            .setHtmlContent(
                                "<html><body><h1>Admin "
                                    + "Page</h1><p>Today is <%=new java.util.Date().toString()%>"
                                    + "</p></body></html>")
                            .setCreateTime(new Date())))));
    //
    //Store npm package names
    //
    listOfObjectToPersist.add(
        new JwConfig()
            .setConfigName(DB_CONFIG_KEY_NPM_PACKAGE_NAME)
            .setConfigValue("compass-importer"));
    listOfObjectToPersist.add(
        new JwConfig().setConfigName(DB_CONFIG_KEY_NPM_PACKAGE_NAME).setConfigValue("node-sass"));
    listOfObjectToPersist.add(
        new JwConfig().setConfigName(DB_CONFIG_KEY_NPM_PACKAGE_NAME).setConfigValue("exit-code"));
    listOfObjectToPersist.add(
        new JwConfig()
            .setConfigName(DB_CONFIG_KEY_NPM_PACKAGE_NAME)
            .setConfigValue("google-closure-compiler"));
    //
    // Persist the entities
    //
    JwDbEntityManager.getInstance().persist(listOfObjectToPersist);
    JLog.info("Data base pre population complete");
  }

  public List<WebPage> getAllWebPages() {
    //
    // Collect all the webPages in the database as of now
    //
    final long currentTimeMillis = System.currentTimeMillis();
    if (this.lastWebPageListingTime == Cnst.CACHE_VALIDITY_TIME_NOT_STARTED
        || (currentTimeMillis - this.lastWebPageListingTime) > Cnst.CACHE_VALIDITY_TIME_MILLI) {
      this.currentWebPageCache =
          JwDbEntityManager.getInstance().streamData(WebPage.class).collect(Collectors.toList());
      JLog.info("WebPage Cache : ");
      this.currentWebPageCache.forEach(e -> JLog.info(e.toString()));
      this.lastWebPageListingTime = System.currentTimeMillis();
    }

    final List<WebPage> returnValue = this.currentWebPageCache;

    return returnValue;
  }
}
