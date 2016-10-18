package me.saptarshidebnath.jwebsite.servlet;

import me.saptarshidebnath.jwebsite.cms.JwCms;
import me.saptarshidebnath.jwebsite.db.JwDbEntityManager;
import me.saptarshidebnath.jwebsite.db.entity.page.WebPage;
import me.saptarshidebnath.jwebsite.db.entity.website.JwConfig;
import me.saptarshidebnath.jwebsite.utils.Cnst;
import me.saptarshidebnath.jwebsite.utils.Utils;
import me.saptarshidebnath.jwebsite.utils.WebInstanceConstants;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.saptarshidebnath.jwebsite.utils.Cnst.DB_WEBPAGE_ADMIN_PG_UI;
import static me.saptarshidebnath.jwebsite.utils.Utils.doRequestContainsAttribute;
import static me.saptarshidebnath.jwebsite.utils.Utils.singletonCollector;

/**
 * The Controller servlet for jWebsite. The Servlet handles all the {@link
 * javax.servlet.http.HttpServletRequest HttpServletRequest} and redirects all the users to the
 * correct handlers.
 */
@WebServlet(value = "*.html", name = "URL handler servlet")
public class UrlHandler extends HttpServlet {

  private String attributeForwardedKey;
  private String attributeForwardedValue;
  /** Cached data of relative path where JSP is dumped. */
  private String jspDumpReplativePath;
  /**
   * {@link java.util.HashMap} of WebPages keyed by url path currently configured in the web
   * application
   */
  private Map<String, WebPage> universeOfPages;

  /**
   * The following method handles any GET request to the web application
   *
   * @param request Takes a reference of {@link javax.servlet.http.HttpServletRequest
   *     HttpServletRequest} this is sent by the web server created from the http request sent by
   *     the HttpClient [generally a browser like Chrome]
   * @param response Takes a reference {@link javax.servlet.http.HttpServletResponse} which is going
   *     to be populated and sent to the HttpClient [Generally a browser like Chrome]
   * @throws ServletException Throws {@link javax.servlet.ServletException ServletException} to
   *     report any Servlet Error
   * @throws IOException Throws {@link java.io.IOException IOException} to report any IO Exception
   * @see javax.servlet.http.HttpServlet
   * @see javax.servlet.http.HttpServletRequest
   * @see javax.servlet.http.HttpServletResponse
   */
  @Override
  public void doGet(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {

    this.processRequest(request, response);
  }

  private void processRequest(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {

    final String currentPath = request.getRequestURI();

    JLog.info("Requested for Path : " + currentPath);

    final boolean newRequest = !doRequestContainsAttribute(request, this.attributeForwardedKey);
    if (newRequest) {
      if (this.universeOfPages.keySet().contains(currentPath)) {
        Utils.setAttribute(request, this.attributeForwardedKey, this.attributeForwardedValue);
        final String jspFileName =
            this.jspDumpReplativePath
                + "/"
                + this.universeOfPages.get(currentPath).getJspFileName();

        JLog.info("Forwarding request >> " + currentPath + " to : " + jspFileName);
        request.getRequestDispatcher(jspFileName).include(request, response);
      } else if (this.universeOfPages.keySet().size() == 1) {
        Utils.setAttribute(request, this.attributeForwardedKey, this.attributeForwardedValue);
        JLog.info("Only 1 URL found. Redirecting user to the admin page");
        response.sendRedirect(DB_WEBPAGE_ADMIN_PG_UI);
      } else {
        //
        // If no such url end point is configured, then try to forward the request to the default
        // handler
        //
        JLog.warning("Resource not found : " + currentPath);
      }
    } else {
      JLog.info("Request is already forwarded. Not forwarding again !!");
    }
  }

  /** Method is called when the current servlet is destroyed. */
  @Override
  public void destroy() {
    JLog.info("Bye Bye !!");
  }

  /**
   * The init method is called once before serving the first request. This method is used to
   * initiate resources for the servlet.
   */
  @Override
  public void init() {
    //
    // Cached a copy of WebPages
    //
    this.universeOfPages =
        Collections.unmodifiableMap(
            JwCms.getInstance()
                .getAllWebPages()
                .parallelStream()
                .collect(Collectors.toMap(WebPage::getUrlPath, Function.identity())));
    JLog.info("Web Page information cached.");

    this.jspDumpReplativePath =
        File.separator
            + JwDbEntityManager.getInstance()
                .streamData(JwConfig.class)
                .filter(e -> e.getConfigName().equalsIgnoreCase(Cnst.DB_CONFIG_KEY_JSP_LOCATION))
                .collect(singletonCollector())
                .getConfigValue();

    this.attributeForwardedKey =
        WebInstanceConstants.INST.getValueFor(Cnst.WIC_KEY_REQ_FORWARD_INDICATOR_ATTR_NAME);

    this.attributeForwardedValue =
        WebInstanceConstants.INST.getValueFor(Cnst.WIC_KEY_REQ_FORWARD_INDICATOR_ATTR_VALUE);

    JLog.info("JSP dump location : " + this.jspDumpReplativePath);
    JLog.info("Initiated !!");
  }
}
