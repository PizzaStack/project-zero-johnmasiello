package com.revature.project_0;

import java.util.Scanner;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.view.ConsoleView;

import org.junit.Before;
import org.junit.Test;

public class AppTest 
{
	private String consoleInput;
	
	@Before
	public void initScannerResource() {
		consoleInput = "1\n" // choose customer
				+ "1\n"			//sign in
				+ "John_86\n" 	// enter username
				+ "password\n" 	// enter password
				+ "3\n"			// sign out"
				+ "4\n"			// exit program
				+ "";
		
		consoleInput = "2\n" // choose employee
				+ "1\n"			// login
				+ "Banker1\n" 	// enter username
				+ "3\n"			// view by customer"
				+ "0\n"			// enter customer id
				+ "4\n"			// go back to alternate login screen
				+ "3\n"			// go back to root console
				+ "4\n"			// exit program
				+ "";
	}
			
	@Test 
	public void testAppWithConsole() {
		Scanner scanner = new Scanner(consoleInput);
		new ConsoleView(new Repository(), scanner).goLive();
		
		scanner.close();
	}
}
