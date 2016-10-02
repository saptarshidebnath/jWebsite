package me.saptarshidebnath.jwebsite.utils.jlog;

import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.StreamHandler;

import static me.saptarshidebnath.jwebsite.utils.Cnst.DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH;
import static me.saptarshidebnath.jwebsite.utils.Cnst.REPLACEMENT_LEVEL_VALUE;
import static me.saptarshidebnath.jwebsite.utils.Cnst.TEXT_ENCODING_UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;

/** */
public class JLogTest {
  private static String testMessage = null;
  private static String loggedMessageRegexWihtoutException = null;
  private static String loggedMessageStackTraceRegex = null;
  private static String exceptionMessage = null;
  private StreamHandler testHandler = null;
  private ByteArrayOutputStream logHandlerBAOS = null;
  private ConsoleFormatter formatter = null;

  @org.junit.BeforeClass
  public static void setUp() throws Exception {
    Class.forName("me.saptarshidebnath.jwebsite.utils.jlog.JLog");
    JLogTest.testMessage = "Test Message " + new Date();
    JLogTest.loggedMessageRegexWihtoutException =
        "^\\[\\s{1}"
            + REPLACEMENT_LEVEL_VALUE
            + "\\s{1}\\]\\s{1}-\\s{1}\\[\\s{1}([A-Z]{1}[a-z]{2}\\s{1}){2}[0-9]{2}\\s{1}([0-9]{2}:){2}[0-9]{2}\\s{1}[A-Z]{3}\\s{1}[0-9]{4}\\s{1}\\]\\s{1}-\\s\\[.*,\\s[0-9]+\\s\\]\\s>>\\s"
            + JLogTest.testMessage
            + "$";
    JLogTest.loggedMessageStackTraceRegex =
        "^\\s+at\\s{1}[a-zA-Z]+(\\.[a-zA-Z0-9\\-\\$]+)+\\(.*\\)$";
    JLogTest.exceptionMessage = "Test Message " + new Date();
  }

  @org.junit.AfterClass
  public static void tearDown() {}

  @org.junit.Before
  public void beforeTest() throws UnsupportedEncodingException {
    //
    // Refresh the Log handler
    //
    this.logHandlerBAOS = new ByteArrayOutputStream();

    this.formatter = new ConsoleFormatter();
    this.formatter.setDebug(false);
    this.formatter.setStackTraceDepth(DEFAULT_STACK_TRACE_CLASS_NAME_DEPTH - 1);
    final PrintStream ps = new PrintStream(this.logHandlerBAOS, true, TEXT_ENCODING_UTF_8);
    this.testHandler = new StreamHandler(ps, this.formatter);
    JLog.addHandler(this.testHandler);
  }

  @org.junit.After
  public void afterTest() {
    JLog.removeHandler(this.testHandler);
    this.testHandler.close();
  }

  @org.junit.Test
  public void getHandlers() {
    final Handler[] handlersList = JLog.getHandlers();
    assertThat(
        "Checking the count of the handler attached to the log : ", handlersList.length >= 1);
    assertThat(
        "Checking if our handler is attached to the logger or not : ",
        handlersList,
        hasItemInArray(this.testHandler));
  }

  @org.junit.Test
  public void logWithoutException() throws IOException {
    JLog.log(Level.INFO, testMessage);
    this.testLoggerWithoutExceptionWith(Level.INFO, JLogTest.testMessage);
  }

  @org.junit.Test
  public void logWithoutExceptionAndDebugModeSet() throws IOException {
    this.formatter.setDebug(true);
    JLog.log(Level.INFO, testMessage);
    final String[] loggedMessage = getCapturedLog().split(System.lineSeparator());
    assertThat("Checking number of printed", loggedMessage.length, greaterThan(0));
    this.formatter.setDebug(false);
  }

  @org.junit.Test
  public void logWithException() throws IOException {
    JLog.log(Level.INFO, JLogTest.testMessage, new RuntimeException(exceptionMessage));
    this.testLoggerWithExceptionWith();
  }

  @org.junit.Test
  public void infoWithException() throws Exception {
    JLog.info(JLogTest.testMessage, new RuntimeException(exceptionMessage));
    this.testLoggerWithExceptionWith();
  }

  @org.junit.Test
  public void infoWithoutException() throws Exception {
    JLog.info(testMessage);
    this.testLoggerWithoutExceptionWith(Level.INFO, JLogTest.testMessage);
  }

  @org.junit.Test
  public void severeWithoutException() throws Exception {
    JLog.severe(testMessage);
    this.testLoggerWithoutExceptionWith(Level.SEVERE, JLogTest.testMessage);
  }

  @org.junit.Test
  public void severeWithException() throws Exception {
    JLog.severe(JLogTest.testMessage, new RuntimeException(exceptionMessage));
    this.testLoggerWithExceptionWith();
  }

  @org.junit.Test
  public void warningWithOutException() throws IOException {
    JLog.warning(testMessage);
    this.testLoggerWithoutExceptionWith(Level.WARNING, JLogTest.testMessage);
  }

  @org.junit.Test
  public void warningWithException() throws IOException {
    JLog.warning(JLogTest.testMessage, new RuntimeException(exceptionMessage));
    this.testLoggerWithExceptionWith();
  }

  private void testLoggerWithoutExceptionWith(final Level level, final String testMessage)
      throws IOException {
    final String loggedMessage = getCapturedLog();
    //    System.out.println("Log received :-");
    //    System.out.println(loggedMessage);
    //    System.out.println();
    assertThat("Checking log without exception > log end : ", loggedMessage, endsWith(testMessage));
    assertThat(
        "Checking log without exception > start : ",
        loggedMessage,
        startsWith("[ " + level.getLocalizedName() + " ]"));
    Assert.assertTrue(
        "Checking log without exception > with regex to match the generic pattern : ",
        loggedMessage.matches(
            JLogTest.loggedMessageRegexWihtoutException.replaceAll(
                REPLACEMENT_LEVEL_VALUE, level.getLocalizedName())));
  }

  private void testLoggerWithExceptionWith() throws IOException {
    final String[] loggedMessage = getCapturedLog().split(System.lineSeparator());
    final String firstLine = loggedMessage[0];
    assertThat(
        "Checking log with exception > first line for exception message",
        firstLine,
        endsWith(exceptionMessage));
    assertThat(
        "Checking log with exception > Number of line count should be greater than 2",
        loggedMessage.length,
        greaterThan(2));
    //First line is the message
    //Second line is the actual error message
    //From third line the actual stack trace starts
    for (int i = 2; i < loggedMessage.length; i++) {
      Assert.assertTrue(
          "Checking log with exception > checking for existence of stacktrace [" + i + "] :",
          loggedMessage[i].matches(JLogTest.loggedMessageStackTraceRegex));
    }
  }

  private String getCapturedLog() throws IOException {
    this.testHandler.flush();
    return this.logHandlerBAOS.toString(TEXT_ENCODING_UTF_8).trim();
  }
}
