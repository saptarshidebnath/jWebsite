package me.saptarshidebnath.jwebsite.utils;

/**
 Constant class contains all the constants that will be used by other classes.
 */
public class Constants {

  /**
   DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH is the default depth for the stack trace at
   {@link me.saptarshidebnath.jwebsite.utils.jlog.ConsoleFormatter ConsoleFormatter} in
   {@link java.lang.Integer Integer} format
   */
  public static final Integer DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH = 10;
  /**
   {@link java.lang.String String} token that need to be replaced in the REGEX with the
   {@link java.util.logging.Level#INFO}
   */
  public static final String REPLACEMENT_LEVEL_VALUE = "##__REPLACEMENT_LEVEL_VALUE__#";
  /**
   {@link java.lang.String String} value for the web application's default encoding
   */
  public static final String TEXT_ENCODING_UTF_8 = "UTF-8";

}
