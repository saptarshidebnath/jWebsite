package me.saptarshidebnath.jwebsite.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.StreamHandler;

import static org.junit.Assert.assertTrue;

/**
 * Created by Saptarshi on 8/14/2016.
 */
public class JLogTest {
    private static OutputStream logCapturingStream;
    private static StreamHandler customLogHandler;
    private String testMessage = "Test Message";

    @org.junit.Before
    public void setUp() throws Exception {
        Class.forName("me.saptarshidebnath.jwebsite.utils.JLog");
        logCapturingStream = new ByteArrayOutputStream();
        customLogHandler = new StreamHandler(logCapturingStream, JLog.getHandlers()[0].getFormatter());
        JLog.addHandler(customLogHandler);
    }


    public String getTestCapturedLog() throws IOException {
        customLogHandler.flush();
        return logCapturingStream.toString();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        JLog.removeHandler(customLogHandler);
    }

    @org.junit.Test
    public void log() throws Exception {
        String messageRegex = "\\[ " + Level.INFO.getLocalizedName() + " \\] - \\[ ([A-Z]{1}[a-z]{2} ){2}[0-9]{2} ([0-9]{2}:{0,1}){3} [A-Z]{3} [0-9]{4} ] - \\[ .*, [0-9]+ \\] >> " + this.testMessage;
        JLog.log(Level.INFO, this.testMessage);
        String loggedMessage = getTestCapturedLog();
        assertTrue("Checking log end : ", loggedMessage.endsWith(this.testMessage));
        assertTrue("Checking log start : ", loggedMessage.startsWith("[ " + Level.INFO.getLocalizedName() + " ]"));
        assertTrue("Checking generic String pattern : " + messageRegex, loggedMessage.matches(messageRegex));
    }

    @org.junit.Test
    public void log1() throws Exception {

    }

    @org.junit.Test
    public void info() throws Exception {

    }

    @org.junit.Test
    public void info1() throws Exception {

    }

    @org.junit.Test
    public void severe() throws Exception {

    }

    @org.junit.Test
    public void severe1() throws Exception {

    }

    @org.junit.Test
    public void warning() throws Exception {

    }

    @org.junit.Test
    public void warning1() throws Exception {

    }

}