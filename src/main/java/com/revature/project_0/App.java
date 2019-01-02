package com.revature.project_0;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.view.ConsoleView;

public class App 
{	
    public final static void main( String[] args )
    {    	
    	PropertyConfigurator.configure("logging.config");
        try (Scanner scanner = new Scanner(System.in)) {
			new ConsoleView(new Repository(), scanner).goLive();
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
    }
}
