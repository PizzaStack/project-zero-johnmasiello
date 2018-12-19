package com.revature.project_0.view;

import com.revature.project_0.entity.Customer;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.util.Util;

public class CustomerView extends BasicContextMenuView implements Operational {
	private Customer customer;
	private int view;
	private final String[] rootOptions = new String[] {
			"Login",
			"Sign Up"
	};
	private final String[] applicationOptions = new String[] {
			"Sign Out",
			"Create Application"
	};
	private final String[] personalInfoOptions = new String[] {
			"Sign Out",
			"Enter Personal Information"
	};
	private final String[] mainOptions = new String[] {
			"Sign Out",
			"View Accounts",
			"Update Personal Info"
	};
	
	private final int ROOT 						= 0;
	private final int FILL_OUT_APPICATION		= 1;
	private final int FILL_OUT_PERSONAL_INFO 	= 2;
	private final int MAIN						= 3;
	
	public CustomerView(Repository repository) {
		view = ROOT;
		customer = new Customer(repository);
	}
	
	@Override
	public String[] provideCurrentMenu() {
		switch (view) {
		case ROOT:
			return rootOptions;
		case FILL_OUT_APPICATION:
			return applicationOptions;
		case FILL_OUT_PERSONAL_INFO:
			return personalInfoOptions;
		case MAIN:
			return mainOptions;
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
				if (customer.getCustomerSelfIdentify().signInSuccessful(name,  password)) {
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
					if (!customer.getCustomerSelfIdentify().isValidUsername(name)) {
						System.out.println("Username Is Not Valid");
					} else if (!customer.getCustomerSelfIdentify().isUniqueUsername(name)) {
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
					if (customer.getCustomerSelfIdentify().isValidPassword(password))
						break;
					System.out.println(String.valueOf(numberOfTriesLeft) +
							" more attempts...\n");
				}
				if (numberOfTriesLeft < 0)
					break;
				if (customer.getCustomerSelfIdentify().createNewCustomer(name, password)) {
					System.out.print("User Login Created Successfully\n");
					view = FILL_OUT_APPICATION;
				} else {
					System.out.print("System Error");
				}
				break;
			default:
				if (customer.isSignedIn())
					signCustomerOut();
				return false;
			} break;
		case FILL_OUT_APPICATION:
			switch (choice) {
			case 1: 
				signCustomerOut();
				break;
			case 2:
				// TODO fill out account application
				break;
			default:
				signCustomerOut();
			} break;
		case FILL_OUT_PERSONAL_INFO:
			switch (choice) {
			case 1: 
				signCustomerOut();
				break;
			case 2:
				// TODO fill out personal info
				break;
			default:
				view = FILL_OUT_APPICATION;
			} break;
		case MAIN:
			switch (choice) {
			case 1:
				signCustomerOut();
				break;
			case 2:
				// todo view accounts
				break;
			case 3:
				// todo update personal info
				break;
			default:
				// todo determine back navigation by setting view
			} break;
		default: 
			signCustomerOut();
			return false;
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
		.append(Util.printMember(customer.getCustomerSelfIdentify().getUserName()))
		.toString();
	}
}