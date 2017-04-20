package br.ufpe.cin.eseg.qaservice.util;

import org.apache.log4j.Logger;

public class LoggerQAS {
	
	private static LoggerQAS instance;
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(LoggerQAS.class.getName());
	
	private LoggerQAS() {
		
	}
	
	public static LoggerQAS getLoggerInstance(){
		if (instance == null) {
			instance = new LoggerQAS();
		}
		
		return instance;
	}
	
	public void logError(String error) {
		log.error(error);
	}
	
	public void logInfo(String info) {
		log.info(info);
	}

}
