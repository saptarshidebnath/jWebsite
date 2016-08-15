package me.saptarshidebnath.jwebsite.utils;

import java.util.Date;
import java.util.logging.*;

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


    // The static block initiates all the magic.
    static {
        //Allocate the Logger class
        JLog.logger = Logger.getAnonymousLogger();

        //
        // Create the console handlet
        //
        ConsoleHandler handler = new ConsoleHandler();
        //
        // set a new Formatter with the new custom formatter
        //
        handler.setFormatter(new Formatter() {

            /**
             * Return the Stack Trace element by the depth. This is internally used by the
             * Formatter to get the  calling method and calling class
             *
             * @param depth a integer determining the depth, starts at 0
             * @return reference of the class @{@link StackTraceElement}
             */
            private StackTraceElement getStackTraceElementByDepth(int depth) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

                //Used for debugging to find out exactly which stack trace element is giving the correct data about the calling method
/*
                for (int i = 0; i < stackTrace.length; i++) {
                    System.out.println("Step >> " + i + " : " + stackTrace[i].getClassName() + "." + stackTrace[i].getMethodName() + ", " + stackTrace[i].getLineNumber());
                }
*/
                return stackTrace[depth];
            }

            /**
             * This method calls the @getStackTraceElementByDepth and computes the class name method name and line number
             * Currently the depth is statically set to 9
             *
             * @return String response in the following format "[fully qualified class name].[method name], [line number]"
             */
            private String getCallingClassAndMethodName() {
                //
                // Computed by trial and error method
                //
                int depthNumber = 10;
                StackTraceElement callingDetails = this.getStackTraceElementByDepth(depthNumber);
                String response = callingDetails.getClassName() + "."
                        + callingDetails.getMethodName() + ", "
                        + callingDetails.getLineNumber();
                return response;
            }

            /**
             * The format method being overridden to format the logger.
             *
             * @param record Input @{@link LogRecord}
             * @return @{@link String} as response
             */
            @Override
            public String format(LogRecord record) {
                String response = "[ " + record.getLevel().getLocalizedName() + " ] - "
                        + "[ " + new Date() + " ] - "
                        + "[ " + this.getCallingClassAndMethodName() + " ] >> "
                        + record.getMessage();
                return response;
            }
        });
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
