package me.saptarshidebnath.jwebsite.servlet;

import me.saptarshidebnath.jwebsite.cms.JwCms;
import me.saptarshidebnath.jwebsite.db.JwDbEntityManager;
import me.saptarshidebnath.jwebsite.db.entity.page.WebPage;
import me.saptarshidebnath.jwebsite.db.entity.website.JwConfig;
import me.saptarshidebnath.jwebsite.utils.Cnst;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static me.saptarshidebnath.jwebsite.utils.Utils.singletonCollector;

/**
 * The Controller servlet for jWebsite. The Servlet handles all the {@link
 * javax.servlet.http.HttpServletRequest HttpServletRequest} and redirects all the users to the
 * correct handlers.
 */
@WebServlet(value = "/*", name = "URL handler servlet")
public class UrlHandler extends HttpServlet {

  /** Cached data of relative path where JSP is dumped. */
  private String jspDumpReplativePath;

  private Map<String, String> urlVsJspName;
  /** list of WebPages currently configured in the webapplication */
  private List<WebPage> universeOfPages;

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
    final String currentRequestUri = request.getRequestURI();
    JLog.info("Requested for URI : " + currentRequestUri);
    //
    // If there are not URL defined. then its a news install with no data installed
    //

    final String jspFileName =
        this.universeOfPages
            .parallelStream()
            .filter(e -> e.getUrlPath().equalsIgnoreCase(currentRequestUri))
            .collect(singletonCollector())
            .getJspFileName();
    JLog.info("Forwarding request to : " + jspFileName);

    final RequestDispatcher requestDispatcher =
        this.getServletContext()
            .getRequestDispatcher(this.jspDumpReplativePath + "/" + jspFileName);
    requestDispatcher.forward(request, response);
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
    this.universeOfPages = JwCms.getInstance().getAllWebPages(true);
    this.jspDumpReplativePath =
        JwDbEntityManager.getInstance()
            .streamData(JwConfig.class)
            .filter(e -> e.getConfigName().equalsIgnoreCase(Cnst.DB_CONFIG_KEY_JSP_LOCATION))
            .collect(singletonCollector())
            .getConfigValue();
    JLog.info("Initiated !!");
  }
}
