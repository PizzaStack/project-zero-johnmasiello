package com.revature.project_0.io;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	public final static String TRANSACTIONS_LOGGER_NAME = "TRANSACTIONS";
	public final static Logger transactionsLogger = Logger.getLogger(TRANSACTIONS_LOGGER_NAME);
	
	static {
		PropertyConfigurator.configure("logging.config");
	}
}
