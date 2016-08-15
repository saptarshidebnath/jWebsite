package me.saptarshidebnath.jwebsite.utils;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created by Saptarshi on 8/14/2016.
 */
public class JLog {

    private static Logger logger;

    static {
        logger = Logger.getAnonymousLogger();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {

            private StackTraceElement getStackTrakElementByDepth(int depth) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();


                //Used for debuging to find out exactly which stack trace element is giving the correct data about the calling method
                for (int i = 0; i < stackTrace.length; i++) {
                    System.out.println("Step >> " + i + " : " + stackTrace[i].getClassName() + "." + stackTrace[i].getMethodName() + ", " + stackTrace[i].getLineNumber());
                }

                return stackTrace[depth];
            }

            private StringBuilder getCallingClassAndMethodName() {
                StackTraceElement callingDetails = this.getStackTrakElementByDepth(9);
                StringBuilder buffer = new StringBuilder();
                buffer.append(callingDetails.getClassName()).append(".");
                buffer.append(callingDetails.getMethodName()).append(", ");
                buffer.append(callingDetails.getLineNumber());
                return buffer;
            }

            @Override
            public String format(LogRecord record) {
                StringBuilder buffer = new StringBuilder(1000);
                buffer.append("[").append(record.getLevel().getLocalizedName()).append("] - ");
                buffer.append("[ ").append(new Date()).append(" ] - ");
                buffer.append("[ ").append(this.getCallingClassAndMethodName()).append(" ] >> ");
                return buffer.toString();
            }
        });
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
    }
}
