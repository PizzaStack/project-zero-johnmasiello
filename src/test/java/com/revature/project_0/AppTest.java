package com.revature.project_0;

import java.util.NoSuchElementException;
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
		consoleInput = testTwoCustomerJointAccountSucceeds();
	}
	
	private String testOneAccount() {
		String loginUser = 
				"1\n"
				+ "2\n"
				+ "Johnny\n"
				+ "depp00\n";
			
			String givePersonalInfo = loginUser 
				+ "2\n"
				+ "John\n"
				+ "Depp\n"
				+ "x\n"
				+ "06-14-1967\n"
				+ "123-22-1234\n"
				+ "888-123-7777\n"
				+ "jd@gmail.com\n"
				+ "Amber Heard\n"
				+ "1\n";
			
			String openNewAccount = givePersonalInfo
				+ "2\n"
				+ "Bills\n"
				+ "1\n"
				+ "1\n"
				+ "1\n";
			
			String signOutCustomer = openNewAccount
				+ "4\n"
				+ "3\n";
			
			String signInAdmin = signOutCustomer
				+ "3\n"
				+ "1\n"
				+ "ADMIN\n";
			
			String manageApplicationsAsAdmin = signInAdmin
				+ "1\n"
				+ "1\n"
				+ "0\n"
				+ "4\n"
				+ "2\n";
			
			String testDepositIntoAccountAsAdmin_1 = manageApplicationsAsAdmin
				+ "2\n"		// Manage Application
				+ "2\n"		// 1 approve/ 2 deny
				+ "0\n"
				+ "3\n"
				+ "1\n"		// Manage Account
				+ "1\n"
				+ "0\n"
				+ "50.55\n"
				+ "y\n";
					
			String testDepositIntoAccountAsAdmin_2 = manageApplicationsAsAdmin
				+ "2\n"		// Manage Application
				+ "1\n"		// 1 approve/ 2 deny
				+ "0\n"
				+ "3\n"
				+ "1\n"		// Manage Account
				+ "1\n"
				+ "0\n"
				+ "50.55\n";
			
			String testTransferFundsIntoSameAccountFails_1 = testDepositIntoAccountAsAdmin_1
				+ "4\n"		// Refresh Accounts
				+ "3\n"
				+ "0\n"
				+ "0\n";
			
			String testTransferFundsIntoSameAccountFails_2 = testDepositIntoAccountAsAdmin_2
				+ "4\n"		// Refresh Accounts
				+ "3\n"
				+ "0\n"
				+ "0\n";
		
			String testOverdraftWithdrawal_1 = testDepositIntoAccountAsAdmin_1
				+ "4\n"
				+ "2\n"
				+ "0\n"
				+ "100.01\n";
			
			String testOverdraftWithdrawal_1b = testDepositIntoAccountAsAdmin_1
					+ "4\n"
					+ "2\n"
					+ "0\n"
					+ "40.01\n";
			
			return testOverdraftWithdrawal_1b;
	}
			
	private String testOneCustomerJointAccountFails() {
		String loginUser = 
				"1\n"
				+ "2\n"
				+ "Johnny\n"
				+ "depp00\n";
			
			String givePersonalInfo = loginUser 
				+ "2\n"
				+ "John\n"
				+ "Depp\n"
				+ "x\n"
				+ "06-14-1967\n"
				+ "123-22-1234\n"
				+ "888-123-7777\n"
				+ "jd@gmail.com\n"
				+ "Amber Heard\n"
				+ "1\n";
			
			String openNewAccount = givePersonalInfo
				+ "2\n"
				+ "Bills\n"
				+ "1\n"
				+ "2\n"			// 1 individual 2 joint
				+ "123-21-6789\n"
				+ "1\n";
			
			String signOutOfFirstCustomer = openNewAccount
				+ "4\n"
				+ "3\n";
			
			String adminSignIn = signOutOfFirstCustomer
				+ "3\n"
				+ "1\n"
				+ "ADMIN_01\n";
			
			String approveAccounts1 = adminSignIn
				+ "1\n"
				+ "1\n"		// 1 approve 2 deny
				+ "0\n";
			
			String approveAccounts2 = adminSignIn
					+ "1\n"
					+ "2\n"		// 1 approve 2 deny
					+ "0\n";
					
			return approveAccounts2;
	}
	
	private String testTwoCustomerJointAccountSucceeds() {
		String openAccountUser1, createProfileUser2;
		
		{
		String loginUser = 
				"1\n"
				+ "2\n"
				+ "Johnny\n"
				+ "depp00\n";
			
			String givePersonalInfo = loginUser 
				+ "2\n"
				+ "John\n"
				+ "Depp\n"
				+ "x\n"
				+ "06-14-1967\n"
				+ "123-22-1234\n"
				+ "888-123-7777\n"
				+ "jd@gmail.com\n"
				+ "Amber Heard\n"
				+ "1\n";
			
			String openNewAccount = givePersonalInfo
				+ "2\n"
				+ "Bills\n"
				+ "1\n"
				+ "2\n"			// 1 individual 2 joint
				+ "312-22-4321\n"
				+ "1\n";
			
			String signOutCustomer = openNewAccount
				+ "4\n"
				+ "3\n";
			
			openAccountUser1 = signOutCustomer;
		}
		{
			String loginUser = 
					"1\n"
					+ "2\n"
					+ "Amber:)\n"
					+ "supersecure\n";
				
				String givePersonalInfo = loginUser 
					+ "3\n"
					+ "Amber\n"
					+ "Heard\n"
					+ "A\n"
					+ "12-25-1975\n"
					+ "312-22-4321\n"
					+ "888-123-7777\n"
					+ "ah@gmail.com\n"
					+ "Johnny Depp\n"
					+ "1\n";
				
				String signOutCustomer = givePersonalInfo
					+ "4\n"
					+ "3\n";
				
				createProfileUser2 = signOutCustomer;
		}	
		String jointCustomerProfileAndAccountCreation = openAccountUser1 + createProfileUser2;  
		String adminSignIn = jointCustomerProfileAndAccountCreation
			+ "3\n"
			+ "1\n"
			+ "ADMIN_01\n";
		String approveAccounts1 = adminSignIn
			+ "1\n"
			+ "1\n"		// 1 approve 2 deny
			+ "0\n";
		
		return approveAccounts1;
	}
	
	@Test 
	public void testAppWithConsole() {
		Scanner scanner = new Scanner(consoleInput);
		try {
			new ConsoleView(new Repository(), scanner).goLive(); 
		} catch (NoSuchElementException e) {
			System.out.println();
			System.out.println("...Exiting Banking App, status 0");
		}
		scanner.close();
	}
}
