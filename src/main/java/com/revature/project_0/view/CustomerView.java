package com.revature.project_0.view;

import com.revature.project_0.entity.Customer;
import java.util.Scanner;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.util.Util;

public class CustomerView extends BasicContextMenuView implements Operational {
	private Customer customer;
	private int view;
	private final String[] rootOptions = new String[] {
			"Login",
			"Sign Up",
			BACK
	};
	private final String[] mainOptions = new String[] {
			"Sign Out",
			"View Accounts",
			"Open New Account",
			"Update Personal Info"
	};
	private final String[] applicationSubmitMenu = new String[] {
			"Cancel",
			"Submit"
	};
	
	private final int ROOT 							= 0;
	private final int COMPLETE_APPICATION			= 1;
	private final int MAIN							= 3;
	
	public CustomerView(Repository repository, Scanner scanner) {
		super(scanner);
		view = ROOT;
		customer = new Customer(repository);
	}
	
	@Override
	public String[] provideCurrentMenu() {
		switch (view) {
		case ROOT:
			return rootOptions;
		case MAIN:
			return mainOptions;
		case COMPLETE_APPICATION:
			return applicationSubmitMenu;
		default:
			return nullOptions;
		}
	}

	@Override
	public boolean consumedChoice(int choice) {
		String name = null;
		String password = null;
		switch (view) {
		case ROOT:
			switch (choice) {
			case 1:
				System.out.print("Please Enter Username: ");
				name = scanner.nextLine();
				System.out.println();
				System.out.print("Please Enter Password: ");
				password = scanner.nextLine();
				System.out.println();
				if (customer.signInSuccessful(name,  password)) {
					System.out.println("Login Successful\n");
					view = MAIN;
				} else {
					System.out.println("Login Failed\n");
				}
				break;
			case 2:
				int numberOfTriesLeft = 3;
				while (numberOfTriesLeft-- > 0) {
					System.out.print("Please Enter New Username (Length > 6): ");
					name = scanner.nextLine();
					System.out.println();
					if (!customer.isValidUsername(name)) {
						System.out.println("Username Is Not Valid");
					} else if (!customer.isUniqueUsername(name)) {
						System.out.println("Username taken already. Please choose another");
					} else
						break;
					System.out.println(String.valueOf(numberOfTriesLeft) +
							" more attempts...\n");
				}
				if (numberOfTriesLeft < 0)
					break;
				numberOfTriesLeft = 3;
				while (numberOfTriesLeft-- > 0 ) {
					System.out.print("Please Enter New Password (Length > 6): ");
					password = scanner.nextLine();
					System.out.println();
					if (customer.isValidPassword(password))
						break;
					System.out.println(String.valueOf(numberOfTriesLeft) +
							" more attempts...\n");
				}
				if (numberOfTriesLeft < 0)
					break;
				if (customer.createNewCustomer(name, password)) {
					System.out.print("User Login Created Successfully\n");
					view = MAIN;
				} else {
					System.out.print(Operational.VISIBLE_SYSTEMS_ERROR);
				}
				break;
			case 3:
				if (customer.isSignedIn())
					signCustomerOut();
				return false;
			} break;
			
		case MAIN:
			switch (choice) {
			case 1:
				signCustomerOut();
			case 2:
				break;
			}
		}
		return true;
	}
	
	private void signCustomerOut() {
		customer.signOut();
		System.out.println("Logout Successful");
		view = ROOT;
	}
	
	@Override
	public String provideSalutation() {
		if (!customer.isSignedIn())
			return "(Member)";
		return new StringBuilder()
				.append("(Member) ")
		.append(Util.printMember(customer.getUserName()))
		.toString();
	}
}