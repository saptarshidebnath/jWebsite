package me.saptarshidebnath.jwebsite.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

/** Constant class contains all the constants that will be used by other classes. */
public class Cnst {

  /**
   * DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH is the default depth for the stack trace at {@link
   * me.saptarshidebnath.jwebsite.utils.jlog.ConsoleFormatter ConsoleFormatter} in {@link
   * java.lang.Integer Integer} format
   */
  public static final Integer DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH = 10;
  /**
   * {@link java.lang.String String} token that need to be replaced in the REGEX with the {@link
   * java.util.logging.Level#INFO}
   */
  public static final String REPLACEMENT_LEVEL_VALUE = "##__REPLACEMENT_LEVEL_VALUE__#";
  /** {@link java.lang.String String} value for the web application's default encoding */
  public static final String TEXT_ENCODING_UTF_8 = "UTF-8";

  /** {@link String} default config file for the website */
  public static final String DEFAULT_CONFIG_JSON = "initialconfig.json";
  /** A static {@link Gson Gson} object with pretty printing enabled. */
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static final String ENV_DATABASE_URL = "DATABASE_URL";

  public static final String ENV_DATABASE_URL_HEROKU_TOEKNEIZER = ":@/";

  public static final String JPA_DB_CONF_NAME = "JwDbConfiguration";

  public static final String DB_CONFIG_KEY_JSP_LOCATION = "KEY_JSP_DUMP_LOCATION";
  public static final String DB_CONFIG_VALUE_JSP_LOCATION =
      "WEB-INF" + File.separator + "jw" + File.separator + "jsp";
}
