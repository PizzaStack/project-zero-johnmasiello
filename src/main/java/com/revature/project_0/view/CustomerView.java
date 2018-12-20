package com.revature.project_0.view;

import com.revature.project_0.entity.Customer;
import com.revature.project_0.entity.Validating;

import java.util.Scanner;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.PersonalInfoModel;
import com.revature.project_0.util.Util;

public class CustomerView extends InputtingContextMenuView implements Operational {
	private Customer customer;
	private PersonalInfoModel personalInfoModelValidator;
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
	private final String[] transactionsMenu = new String[] {
			"Deposit",
			"Withdraw",
			"Transfer",
			BACK
	};
	private final String[] applicationSubmitMenu = new String[] {
			"Submit",
			"Cancel"
	};
	
	private final int ROOT 							= 0;
	private final int COMPLETE_APPLICATION			= 1;
	private final int COMPLETE_DETAILS				= 2;
	private final int MAIN							= 3;
	private final int TRANSACTION					= 4;
	
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
		case COMPLETE_APPLICATION:
		case COMPLETE_DETAILS:
			return applicationSubmitMenu;
		case TRANSACTION:
			return transactionsMenu;
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
				break;
			case 2:
				if (!customer.fetchAccounts(customer.getCustomerId()))
					System.out.println("No Accounts On Record");
				else {
					customer.getAccounts().forEach(($) -> {
						System.out.println($); 
						System.out.println();
					});
					view = TRANSACTION;
				}
				break;
			case 3:
				if (!customer.hasPersonalRecordOnFile()) {
					System.out.println("We Appreciate Your Business");
					System.out.println("We Would Like To Know A Little More About You\n");
					fillOutPersonalInformation();
				} else {
					System.out.println("Let's Help You Apply For A New Account\n");
					applyForAccount();
				}
				break;
			case 4:
				System.out.println("Stay Up-To-Date\n");
				fillOutPersonalInformation();
				break;
			} break;
			
		case TRANSACTION:
			switch (choice) {
			case 1:
				break;
			} break;
			
		case COMPLETE_APPLICATION:
			switch (choice) {
			case 1:
				System.out.println("Application Submitted by User");
				System.out.println("Please Wait For Your Account To Be Created");
				if (!customer.createApplication(customer.getApplication())) {
					System.out.println(Operational.VISIBLE_SYSTEMS_ERROR);
				}
				view = MAIN;
				break;
			case 2:
				System.out.println("Application Canceled by User");
				view = MAIN;
				break;
			} break;
			
		case COMPLETE_DETAILS:
			switch (choice) {
			case 1:
				if (!customer.createPersonalInfo(customer.getPersonalInfoModel())) {
					System.out.println(Operational.VISIBLE_SYSTEMS_ERROR);
				} else {
					System.out.println("Details Update Successful");					
				}
				view = MAIN;
				break;
			case 2:
				System.out.println("Personal Details Canceled by User");
				customer.resetPersonalInfo();
				view = MAIN;
				break;
			} break;
			
		}
		return true;
	}
	
	private void signCustomerOut() {
		customer.signOut();
		System.out.println("Logout Successful");
		view = ROOT;
	}
	
	private void printAbortMessage() {
		System.out.println("Aborting Update");
	}
	
	private void failPersonalInfoForm() {
		printAbortMessage();
		personalInfoModelValidator = null;
		view = MAIN;
	}
	
	private final Validating validateFirstName = ($) -> {
		return personalInfoModelValidator.setFirstName($);
	};
	private final Validating validateLastName = ($) -> {
		return personalInfoModelValidator.setLastName($);
	};
	private final Validating validateMI = ($) -> {
		if ($.length() == 1) {
			personalInfoModelValidator.setMiddleInitial($.charAt(0));
			return true;
		}
		return false;
	};
	private final Validating validateSSN = ($) -> {
		return personalInfoModelValidator.setLast4ssn($);
	};
	
	private final Validating validateDob = ($) -> {
		return personalInfoModelValidator.setDob(Util.parseDate($));
	};
	private final Validating validatePhone = ($) -> {
		return personalInfoModelValidator.setPhoneNumber($);
	};
	private final Validating validateEmail= ($) -> {
		return personalInfoModelValidator.setEmail($);
	};
	private final Validating validateBeneficiary = ($) -> {
		return personalInfoModelValidator.setBeneficiary($);
	};
	
	private void fillOutPersonalInformation() {
		personalInfoModelValidator = new PersonalInfoModel.Builder()
				.withCustomerId(customer.getCustomerId())
				.build();
		final int numberOfTriesPerField = 3;
		
		System.out.println("Please Enter The Following: \n");
		if (!acceptStringAsTokenWithAttempts("First Name: ",
				"\nMust be at least three letters",
				numberOfTriesPerField,
				validateFirstName)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts("Last Name: ",
				"\nMust be at least three letters",
				numberOfTriesPerField,
				validateLastName)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts("Middle Initial: ",
				"\nMust be at exactly one letter",
				numberOfTriesPerField,
				validateMI)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts(
				String.format("Date Of Birth (%s): ", Util.getPrintableDatePattern()),
				"\nExample, December 1st, 2000 is 12-01-2000",
				numberOfTriesPerField,
				validateDob)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts("SSN (last 4 digits): ",
				"\nExample, xxx-xx-7777 is 7777",
				numberOfTriesPerField,
				validateSSN)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts("Phone Number: (XXX)XXX-XXXX", 
				"Ten Digit Phone Number With Area Code: Example, (888)333-3333", 
				numberOfTriesPerField, 
				validatePhone)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts("Email: ", 
				"Example, john@gmail.com",
				numberOfTriesPerField, 
				validateEmail)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts("Beneficiary, Full Name: ", 
				"Example, John Doe",
				numberOfTriesPerField, 
				validateBeneficiary)) {
			failPersonalInfoForm();
			return;
		}
		customer.setPersonalInfoModel(personalInfoModelValidator);
		personalInfoModelValidator = null;
		System.out.println("\nReady To Submit");
		view = COMPLETE_DETAILS;
	}
	
	private void applyForAccount() {
		ApplicationModel application = null;
		
		// All Done
		customer.setApplicationModel(application);
		view = COMPLETE_APPLICATION;
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