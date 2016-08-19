package me.saptarshidebnath.jwebsite.utils.jlog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import static me.saptarshidebnath.jwebsite.utils.Constants.DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH;

/**
 * Created by Saptarshi on 8/17/2016.
 */
public class JLogConsoleFormatter extends Formatter {

    private int stackTraceDepthforCallingSourceName;
    private boolean debug;

    /**
     * The default constructor sets @stackTraceDepthforCallingSourceName as @{@link me.saptarshidebnath.jwebsite.utils.Constants} DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH
     */
    public JLogConsoleFormatter() {
        this.stackTraceDepthforCallingSourceName = DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH;
        this.debug = false;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Set the stack trace Depth
     *
     * @param depth
     */
    public void setStackTraceDepth(int depth) {
        this.stackTraceDepthforCallingSourceName = depth;
    }

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

        if (this.debug) {
            for (int i = 0; i < stackTrace.length; i++) {
                System.out.println("Step >> " + i + " : " + stackTrace[i].getClassName() + "." + stackTrace[i].getMethodName() + ", " + stackTrace[i].getLineNumber());
            }
        }

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
        int depthNumber = this.stackTraceDepthforCallingSourceName;
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
        Throwable exceptionThrown = record.getThrown();
        String exceptionMessage = exceptionThrown == null ? "" : " !! " + exceptionThrown.getLocalizedMessage();
        String response = "[ " + record.getLevel().getLocalizedName() + " ] - "
                + "[ " + new Date() + " ] - "
                + "[ " + this.getCallingClassAndMethodName() + " ] >> "
                + record.getMessage()
                + exceptionMessage
                + System.lineSeparator() + this.getStackTrace(record.getThrown());
        return response;
    }

    /**
     * Get the StackTrace of an exception in String format.
     *
     * @param ex @{@link Throwable} Takes as input
     * @return @{@link String} formated stack trace
     */
    private String getStackTrace(Throwable ex) {
        String response = "";
        if (ex != null) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            response = errors.toString() + System.lineSeparator();
        }
        return response;
    }

}
