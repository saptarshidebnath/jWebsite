package me.saptarshidebnath.jwebsite.utils.jlog;

import static me.saptarshidebnath.jwebsite.utils.Constants.DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 This is default formatter which is used by the
 {@link me.saptarshidebnath.jwebsite.utils.jlog.JLog JLog} logger.
 */
public class ConsoleFormatter extends Formatter {
  /**
   The Stack trace depth variable contains the depth of Stack trace to extract the calling
   method and class name.
   */
  private int stackTraceDepth;

  /**
   {@link java.lang.Boolean Boolean} value which sets if the class is going to print debug
   information or not.
   */
  private Boolean debug;

  /**
   The default constructor sets Stack trace look up depth as
   {@link me.saptarshidebnath.jwebsite.utils.Constants#DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH }
   */
  public ConsoleFormatter() {
    super();
    this.stackTraceDepth = DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH;
    this.debug = Boolean.FALSE;
  }

  /**
   Set the debug method for the {@link ConsoleFormatter}. As of now, it just prints the
   stacktrace from where it retrieves the calling method and class name.

   @param debug
   This need to be of type @ boolean
   */
  public void setDebug(final boolean debug) {
    this.debug = debug;
  }

  /**
   Set the stack trace Depth.

   @param depth
   Set the depth of the stack trace from where the content of the calling method is going to be
   determined
   */
  public void setStackTraceDepth(final int depth) {
    this.stackTraceDepth = depth;
  }

  /**
   The format method being overridden to format the logger.

   @param record
   Input {@link java.util.logging.LogRecord LogRecord}

   @return String {@link java.lang.String String} as response
   */
  @Override
  public String format(final LogRecord record) {
    final Throwable exceptionThrown = record.getThrown();
    final String exceptionMessage = exceptionThrown == null
                                    ? ""
                                    : " !! "
                                      + exceptionThrown.getLocalizedMessage();
    return "[ "
           + record.getLevel()
                   .getLocalizedName()
           + " ] - "
           + "[ "
           + new Date()
           + " ] - [ "
           + this.getCallingClassAndMethodName()
           + " ] >> "
           + record.getMessage()
           + exceptionMessage
           + System.lineSeparator()
           + this.getStackTrace(record.getThrown());
  }

  /**
   This method calls the @getStackTraceElementByDepth and computes the class name method
   name and line number Currently the depth is statically set to 9

   @return String response me.saptarshidebnath.jwebsite.utils.jlog.format(), 71
   */
  private String getCallingClassAndMethodName() {
    //
    // Computed by trial and error method
    //
    final int depthNumber = this.stackTraceDepth;
    final StackTraceElement callingDetails = this.getStackTraceElementByDepth(depthNumber);
    return callingDetails.getClassName()
           + "."
           + callingDetails.getMethodName()
           + ", "
           + callingDetails.getLineNumber();
  }

  /**
   Get the StackTrace of an exception in String format.

   @param exception
   Takes {@link java.lang.Throwable Throwable } object as input.

   @return {@link java.lang.String String} formatted stack trace.
   */
  private String getStackTrace(final Throwable exception) {
    String response = null;
    if (exception
        == null) {
      response = "";
    } else {
      final StringWriter errors = new StringWriter();
      exception.printStackTrace(new PrintWriter(errors));
      response = errors.toString()
                 + System.lineSeparator();
    }
    return response;
  }

  /**
   Return the Stack Trace element by the depth. This is internally used by the
   Formatter to get the  calling method and calling class

   @param depth
   a integer determining the depth, starts at 0

   @return reference of the class {@link java.lang.StackTraceElement StackTraceElement }
   */
  private StackTraceElement getStackTraceElementByDepth(final int depth) {
    final StackTraceElement[] stackTrace = Thread.currentThread()
                                                 .getStackTrace();

    //Used for debugging to find out exactly which stack trace element is giving the
    // correct data about the calling method

    if (this.debug) {
      for (int i = 0; i
                      < stackTrace.length; i++) {
        System.out.println("Step >> "
                           + i
                           + " : "
                           + stackTrace[i].getClassName()
                           + "."
                           + stackTrace[i].getMethodName()
                           + ", "
                           + stackTrace[i].getLineNumber());
      }
    }
    return stackTrace[depth];
  }

}
