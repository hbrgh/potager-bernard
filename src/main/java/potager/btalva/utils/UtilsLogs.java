package potager.btalva.utils;

import java.io.File;
import java.io.OutputStream;
import java.util.Iterator;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.recovery.ResilientFileOutputStream;

public class UtilsLogs {

	public static void setLogbackLoggingLevel(Level logBackLevel) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory
				.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(logBackLevel);
	}

	public static String renvCurrentLogFile() {
		File clientLogFile;
		FileAppender<?> fileAppender = null;
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		for (Logger logger : context.getLoggerList()) {
			for (Iterator<Appender<ILoggingEvent>> index = logger.iteratorForAppenders(); index.hasNext();) {
				Object enumElement = index.next();
				if (enumElement instanceof FileAppender) {
					fileAppender = (FileAppender<?>) enumElement;
					if (fileAppender != null) {
						clientLogFile = new File(fileAppender.getFile());
						return (clientLogFile.getAbsolutePath());
					}

				}
			}
		}
		return (null);

	}
	
	public static OutputStream getLogbackLogFileOutputStream() {
  		LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
  	    for (ch.qos.logback.classic.Logger logger : context.getLoggerList()) {
  	        for (Iterator<Appender<ILoggingEvent>> index = logger.iteratorForAppenders(); index.hasNext();) {
  	            Appender<ILoggingEvent> appender = index.next();
  
  	            if (appender instanceof FileAppender) {
  	                FileAppender<ILoggingEvent> fa = (FileAppender<ILoggingEvent>)appender;
  	                ResilientFileOutputStream rfos = (ResilientFileOutputStream)fa.getOutputStream();
  	               return(rfos);
  	            }
  	        }
  	    }
  		return(null);
  	}

}
