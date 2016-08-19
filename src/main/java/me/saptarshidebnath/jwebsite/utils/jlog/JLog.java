package me.saptarshidebnath.jwebsite.utils.jlog;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The custom Logger implementation on top of the @{@link Logger} class.
 *
 * @author Saptarshi
 */
public class JLog {

    /**
     * Empty Constructor
     */
    private JLog() {
    }

    /**
     * Static logger
     */
    private static Logger logger;
    private static JLogConsoleFormatter formatter;


    // The static block initiates all the magic.
    static {
        //Allocate the Logger class
        JLog.logger = Logger.getAnonymousLogger();

        //
        // Create the console handler
        //
        ConsoleHandler handler = new ConsoleHandler();
        //
        // Set custom formatter
        //
        JLog.formatter = new JLogConsoleFormatter();
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
     * The @{@link Logger} object used currently only use 1 @{@link ConsoleHandler} only.
     * This method end point can be used to add extra handler as needed.
     *
     * @param handler Any object of @{@link Handler} class or its sub class can be sent as a input Parameter.
     */
    public static void addHandler(Handler handler) {
        JLog.logger.addHandler(handler);
    }

    /**
     * Method end point to remove a @{@link Handler}
     *
     * @param handler Any object of @{@link Handler} class or its sub class can be sent as a input Parameter.
     */
    public static void removeHandler(Handler handler) {
        JLog.logger.removeHandler(handler);
    }

    /**
     * Returns the current array of @{@link Handler} for the current @{@link Logger}
     *
     * @return an Array of all the registered @{@link Handler}
     */
    public static Handler[] getHandlers() {
        return JLog.logger.getHandlers();
    }

    /**
     * The generic overloaded @log method to log WITH exception logging. The custom Logger is based upon @{@link Logger}
     *
     * @param level     The @{@link Level} is the level of the logging message. Please see @{@link java.util.logging.Level.KnownLevel} for Levels you can set
     * @param message   The @{@link String} object which is expected to contain the generic message.
     * @param throwable The @{@link Throwable} object or any of its sub class in case a exception needs to be logged.
     */
    public static void log(Level level, String message, Throwable throwable) {
        JLog.logger.log(level, message, throwable);
    }

    /**
     * The generic overloaded @log method to log WITHOUT exception logging. The custom Logger is based upon @{@link Logger}
     *
     * @param level   The @{@link Level} is the level of the logging message. Please see @{@link java.util.logging.Level.KnownLevel} for Levels you can set
     * @param message The @{@link String} object which is expected to contain the generic message.
     */
    public static void log(Level level, String message) {
        JLog.logger.log(level, message);
    }

    /**
     * The log method for logging @{@link String} messages with @{@link Level} as INFO
     *
     * @param message The @{@link String} object which is expected to contain the generic message.
     */
    public static void info(String message) {
        JLog.logger.log(Level.INFO, message);
    }

    /**
     * The log method for logging @{@link String} messages with @{@link Level} as INFO
     *
     * @param message   The @{@link String} object which is expected to contain the generic message.
     * @param throwable The @{@link Throwable} object or any of its sub class in case a exception needs to be logged.
     */
    public static void info(String message, Throwable throwable) {
        JLog.logger.log(Level.INFO, message, throwable);
    }

    /**
     * The log method for logging @{@link String} messages with @{@link Level} as SEVERE
     *
     * @param message The @{@link String} object which is expected to contain the generic message.
     */
    public static void severe(String message) {
        JLog.logger.log(Level.SEVERE, message);
    }

    /**
     * The log method for logging @{@link String} messages with @{@link Level} as SEVERE
     *
     * @param message   The @{@link String} object which is expected to contain the generic message.
     * @param throwable The @{@link Throwable} object or any of its sub class in case a exception needs to be logged.
     */
    public static void severe(String message, Throwable throwable) {
        JLog.logger.log(Level.SEVERE, message, throwable);
    }

    /**
     * The log method for logging @{@link String} messages with @{@link Level} as WARNING
     *
     * @param message The @{@link String} object which is expected to contain the generic message.
     */
    public static void warning(String message) {
        JLog.logger.log(Level.WARNING, message);
    }

    /**
     * The log method for logging @{@link String} messages with @{@link Level} as WARNING
     *
     * @param message   The @{@link String} object which is expected to contain the generic message.
     * @param throwable The @{@link Throwable} object or any of its sub class in case a exception needs to be logged.
     */
    public static void warning(String message, Throwable throwable) {
        JLog.logger.log(Level.WARNING, message, throwable);
    }
}
