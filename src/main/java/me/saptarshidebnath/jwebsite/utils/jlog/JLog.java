package me.saptarshidebnath.jwebsite.utils.jlog;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The custom Logger implementation on top of the {@link java.util.logging.Logger Logger} class.
 *
 * @author Saptarshi
 * @since V0.1
 */
public class JLog {

  private static Logger logger;
  private static ConsoleFormatter formatter;

  // The static block initiates all the magic.
  static {
    //Allocate the Logger class
    JLog.logger = Logger.getAnonymousLogger();

    //
    // Create the console handler
    //
    final ConsoleHandler handler = new ConsoleHandler();
    //
    // Set custom formatter
    //
    JLog.formatter = new ConsoleFormatter();
    handler.setFormatter(JLog.formatter);
    //
    // Disable all other Handler
    //
    logger.setUseParentHandlers(false);

    //
    // Add the recently created handler
    //
    logger.addHandler(handler);
  }

  /**
   * The {@link Logger} object used currently only use {@link java.util.logging.ConsoleHandler
   * ConsoleHandler} only. This method end point can be used to add extra handler as needed.
   *
   * @param handler Any object of {@link java.util.logging.Handler Handler} class or its sub class
   *     can be sent as a input Parameter.
   */
  public static void addHandler(final Handler handler) {
    JLog.logger.addHandler(handler);
  }

  /**
   * Method end point to remove a registered {@link java.util.logging.Handler Handler}
   *
   * @param handler Any object of {@link java.util.logging.Handler Handler} class or its sub class
   *     can be sent as a input Parameter.
   */
  public static void removeHandler(final Handler handler) {
    JLog.logger.removeHandler(handler);
  }

  /**
   * Returns the current array of {@link java.util.logging.Handler Handler} for the current {@link
   * java.util.logging.Logger Logger}
   *
   * @return an Array of all the registered {@link java.util.logging.Handler Handler}
   */
  public static Handler[] getHandlers() {
    return JLog.logger.getHandlers();
  }

  /**
   * The generic overloaded @log method to log WITH exception logging. The custom Logger is based
   * upon {@link java.util.logging.Logger}
   *
   * @param level The {@link java.util.logging.Level Level} is the level of the logging message.
   * @param message The {@link java.lang.String String} object which is expected to contain the
   *     generic message.
   * @param throwable The {@link java.lang.Throwable} object or any of its sub class in case a
   *     exception needs to be logged.
   */
  public static void log(final Level level, final String message, final Throwable throwable) {
    JLog.logger.log(level, message, throwable);
  }

  /**
   * The generic overloaded {@link #log log} method to log WITHOUT {@link java.lang.Exception
   * Exception} logging. The custom Logger is based upon {@link java.util.logging.Logger Logger}
   *
   * @param level The {@link java.util.logging.Level Level} is the level of the logging message.
   * @param message The {@link java.lang.String String} object which is expected to contain the
   *     generic message.
   */
  public static void log(final Level level, final String message) {
    JLog.logger.log(level, message);
  }

  /**
   * The log method for logging {@link java.lang.String String} messages with {@link
   * java.util.logging.Level#INFO Level#INFO}
   *
   * @param message The {@link java.lang.String String} object which is expected to contain the
   *     generic message.
   */
  public static void info(final String message) {
    JLog.logger.log(Level.INFO, message);
  }

  /**
   * The log method for logging {@link String} messages with {@link Level#INFO}
   *
   * @param message The {@link String} object which is expected to contain the generic message.
   * @param throwable The {@link Throwable} object or any of its sub class in case a exception needs
   *     to be logged.
   */
  public static void info(final String message, final Throwable throwable) {
    JLog.logger.log(Level.INFO, message, throwable);
  }

  /**
   * The log method for logging {@link String} messages with {@link Level#SEVERE}
   *
   * @param message The {@link String} object which is expected to contain the generic message.
   */
  public static void severe(final String message) {
    JLog.logger.log(Level.SEVERE, message);
  }

  /**
   * The log method for logging {@link String} messages with {@link Level#SEVERE }
   *
   * @param message The {@link java.lang.String String} object which is expected to contain the
   *     generic message.
   * @param throwable The {@link Throwable} object or any of its sub class in case a exception needs
   *     to be logged.
   */
  public static void severe(final String message, final Throwable throwable) {
    JLog.logger.log(Level.SEVERE, message, throwable);
  }

  /**
   * The log method for logging {@link String} messages with {@link Level#WARNING}
   *
   * @param message The {@link String} object which is expected to contain the generic message.
   */
  public static void warning(final String message) {
    JLog.logger.log(Level.WARNING, message);
  }

  /**
   * The log method for logging {@link String} messages with {@link Level#WARNING}
   *
   * @param message The {@link String} object which is expected to contain the generic message.
   * @param throwable The {@link Throwable} object or any of its sub class in case a exception needs
   *     to be logged.
   */
  public static void warning(final String message, final Throwable throwable) {
    JLog.logger.log(Level.WARNING, message, throwable);
  }
}
