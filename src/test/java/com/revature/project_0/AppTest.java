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
		String testCustomerSignIn = 
				"1\n" // choose customer
				+ "2\n"			//sign in
				+ "John_00\n" 	// enter username
				+ "password\n" 	// enter password
				+ "1\n"			// sign out"
				+ "3\n"
				+ "4\n";		// exit program
		
		String employeeLoginLogout = 
				"2\n" // choose employee
				+ "1\n"			// login
				+ "Banker1\n" 	// enter username
				+ "3\n"			// view by customer"
				+ "0\n"			// enter customer id
				+ "4\n"			// go back to alternate login screen
				+ "3\n"			// go back to root console
				+ "4\n"			// exit program
				+ "";
		
		String customerSignInAndFillOutInformation = 
				"1\n"							//choose customer
				+ "2\n"							//sign in
				+ "John00\n" 					// enter username
				+ "password\n" 					// enter password
				+ "3\n"							//'open account' ->redirected to personal info form
				+ "John\n"
				+ "QT\n"
				+ "P\n"
				+ "12-01-1995\n"
				+ "777-77-7777\n"
				+ "(888)333-3333\n"
				+ "john@gmail.com\n"
				+ "Jane Doe\n"
				+ "1\n"							//submit
				+ "";
		
		String customerSignInAndCreateApplication = 
				customerSignInAndFillOutInformation 
				+"3\n"									// Apply/Open for an account
				+ "1\n"									// Make it for checking
				+ "1\n"									// Make it for individual
				+ "1\n";									// Submit
		
		String customerAppliesForAccount_EmployeeApproves = 
				customerSignInAndCreateApplication
			+ "1\n"										// sign out
			+ "3\n"										// back
			+ "2\n"
			+ "1\n"										// employee login
			+ "employee_0\n"								// employee handle
			+ "1\n"										// view applications
			+ "1\n"										// approve applications
			+ "0\n"										// enter application id
			+ "3\n"
			+ "4\n"
			+ "2\n";									// Get back to main console
		
		String customerLogsInToCheckAccountAfterItIsApproved = 
				customerAppliesForAccount_EmployeeApproves
			+ "1\n"							//choose customer
			+ "1\n"							//login in as returning customer
			+ "John00\n" 					// enter username
			+ "password\n" 					// enter password
			+ "2\n";						// view accounts
		
		String customerMakesTransactionsWithHerAccounts = 
				customerLogsInToCheckAccountAfterItIsApproved
			+ "1\n"							// make deposit
			+ "1\n"							// in the first account
			+ "5.00\n"						// by amount
			+ "2\n"							// make a withdrawal
			+ "1\n"							// from account
			+ "6.00\n";						// by amount
		
		consoleInput = customerMakesTransactionsWithHerAccounts;
	}
			
	@Test 
	public void testAppWithConsole() {
		Scanner scanner = new Scanner(consoleInput);
		try {
			new ConsoleView(new Repository(), scanner).goLive();
		} catch (Exception ignore) {}
		scanner.close();
	}
}
