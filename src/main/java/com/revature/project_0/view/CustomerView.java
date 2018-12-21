package com.revature.project_0.view;

import com.revature.project_0.entity.Customer;
import com.revature.project_0.entity.FundsTransactionManager;
import com.revature.project_0.entity.TransactionOutcome;

import java.util.List;
import java.util.Scanner;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.AccountType;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.PersonalInfoModel;
import com.revature.project_0.util.Util;

public class CustomerView extends InputtingContextMenuView implements Operational {
	private Customer customer;
	private PersonalInfoModel personalInfoValidator;
	private ApplicationModel applicationValidator;
	private FundsTransactionManager fundsTransactionManager = new FundsTransactionManager();
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
				System.out.println("Stay Up-To-Date");
				fillOutPersonalInformation();
				break;
			} break;
			
		case TRANSACTION:
			AccountInfoModel targetAccount;
			List<AccountInfoModel> accounts = customer.getAccounts();
			int pickAccount;
			double amount;
			StringBuilder snapshotAccounts = new StringBuilder();
			for (int i = 0; i < accounts.size(); i++) {
				snapshotAccounts
				.append(i+1)
				.append("\t")
				.append(accounts.get(i).provideGlimpse())
				.append("\n");
			}
			
			switch (choice) {
			case 1:
				System.out.println(snapshotAccounts.toString());
				System.out.print("Select Account For Deposit: ");
				if (!scanner.hasNextInt()) {
					purgeLine(scanner);
					System.out.println("No Account Selected");
					break;
				}
				pickAccount = scanner.nextInt();
				purgeLine(scanner);
				if (pickAccount > 0 && pickAccount <= accounts.size()) {
					targetAccount = accounts.get(pickAccount - 1);
					
					System.out.print("Enter the amount for Deposit: $");
					if (!scanner.hasNextDouble()) {
						purgeLine(scanner);
						System.out.println("Amount Not Entered");
						break;
					}
					amount = scanner.nextDouble();
					purgeLine(scanner);
					if (amount <= 0) {
						System.out.println("Amount Must Be > 0");
						break;
					}
					switch (fundsTransactionManager.makeDeposit(targetAccount, amount)) {
					case TransactionOutcome.SUCCESS:
						System.out.println("\nDeposit Made Successfully");
						System.out.println(targetAccount.provideGlimpse());
						break;
					case TransactionOutcome.ACCOUNT_FROZEN:
						System.out.println("\nDeposit Unsuccessful");
						System.out.println("Reason: The Account With Id " 
								+ targetAccount.getAccountId()
								+ " Is Not Currently Active");
						break;
					}
					
				} else {
					System.out.println("No Account Selected");
				}
				break;
			case 2:
				System.out.println(snapshotAccounts.toString());
				System.out.print("Select Account For Withdrawal: ");
				if (!scanner.hasNextInt()) {
					purgeLine(scanner);
					System.out.println("No Account Selected");
					break;
				}
				pickAccount = scanner.nextInt();
				purgeLine(scanner);
				if (pickAccount > 0 && pickAccount <= accounts.size()) {
					targetAccount = accounts.get(pickAccount - 1);
					
					System.out.print("Enter the amount for Withdrawal: $");
					if (!scanner.hasNextDouble()) {
						purgeLine(scanner);
						System.out.println("Amount Not Entered");
						break;
					}
					amount = scanner.nextDouble();
					purgeLine(scanner);
					if (amount <= 0) {
						System.out.println("Amount Must Be > 0");
						break;
					}
					switch (fundsTransactionManager.makeWithdrawal(targetAccount, amount)) {
					case TransactionOutcome.SUCCESS:
						System.out.println("\nWithdrawal Made Successfully");
						System.out.println(targetAccount.provideGlimpse());
						break;
					case TransactionOutcome.ACCOUNT_FROZEN:
						System.out.println("\nWithdrawal Unsuccessful");
						System.out.println("Reason: The Account With Id " 
								+ targetAccount.getAccountId()
								+ " Is Not Currently Active");
						break;
					case TransactionOutcome.INSUFFICIENT_FUNDS:
						System.out.println("\nWithdrawal Unsuccessful");
						System.out.println("Reason: InSufficient Funds"); 
						break;
					}
					
				} else {
					System.out.println("No Account Selected");
				}
				break;
			case 3:
				System.out.println(snapshotAccounts.toString());
				System.out.print("Transfer Funds Into Account: ");
				if (!scanner.hasNextInt()) {
					purgeLine(scanner);
					System.out.println("No Account Selected");
					break;
				}
				pickAccount = scanner.nextInt();
				purgeLine(scanner);
				if (pickAccount <= 0 || pickAccount > accounts.size()) {
					System.out.println("No Account Selected");
				}
				targetAccount = accounts.get(pickAccount - 1);
				System.out.print("From Account: ");
				if (!scanner.hasNextInt()) {
					purgeLine(scanner);
					System.out.println("No Account Selected");
					break;
				}
				int pickAccount2 = scanner.nextInt();
				purgeLine(scanner);
				if (pickAccount2 <= 0 || pickAccount2 > accounts.size()) {
					System.out.println("No Account Selected");
				} else if (pickAccount2 == pickAccount)
					System.out.println("Cannot Pick The Same Account");
				AccountInfoModel originAccount = accounts.get(pickAccount2 - 1);
				System.out.print("Enter Transfer Amount: $");
				if (!scanner.hasNextDouble()) {
					purgeLine(scanner);
					System.out.println("Amount Not Entered");
					break;
				}
				amount = scanner.nextDouble();
				purgeLine(scanner);
				if (amount <= 0) {
					System.out.println("Amount Must Be > 0");
					break;
				}
				switch (fundsTransactionManager.makeTransferOfFunds(originAccount, 
						targetAccount, 
						amount)) {
				case TransactionOutcome.SUCCESS:
					System.out.println("\nTransfer Made Successfully");
					System.out.println(targetAccount.provideGlimpse());
					System.out.println(originAccount.provideGlimpse());
					break;
				case TransactionOutcome.ACCOUNT_FROZEN:
					System.out.println("\nTransfer Not Made");
					System.out.println("Reason: The Account With Id " 
							+ originAccount.getAccountId()
							+ " Is Not Currently Active");
					break;
				case TransactionOutcome.RECIPIENT_ACCOUNT_FROZEN:
					System.out.println("\nTransfer Not Made");
					System.out.println("Reason: The Account With Id " 
							+ targetAccount.getAccountId()
							+ " Is Not Currently Active");
					break;
				case TransactionOutcome.INSUFFICIENT_FUNDS:
					System.out.println("\nTransfer Not Made");
					System.out.println("Reason: InSufficient Funds"); 
					break;
				}
				break;
			case 4:
				view = MAIN;
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
					System.out.println("Details Updated Successfully");					
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
		personalInfoValidator = null;
		view = MAIN;
	}
	
	private final Validating validateFirstName = ($) -> {
		return personalInfoValidator.setFirstName($);
	};
	private final Validating validateLastName = ($) -> {
		return personalInfoValidator.setLastName($);
	};
	private final Validating validateMI = ($) -> {
		if ($.length() == 1) {
			personalInfoValidator.setMiddleInitial($.charAt(0));
			return true;
		}
		return false;
	};
	private final Validating validateSSN = ($) -> {
		return personalInfoValidator.setSSN($);
	};
	
	private final Validating validateDob = ($) -> {
		return personalInfoValidator.setDob(Util.parseDate($));
	};
	private final Validating validatePhone = ($) -> {
		return personalInfoValidator.setPhoneNumber($);
	};
	private final Validating validateEmail= ($) -> {
		return personalInfoValidator.setEmail($);
	};
	private final Validating validateBeneficiary = ($) -> {
		return personalInfoValidator.setBeneficiary($);
	};
	
	private void fillOutPersonalInformation() {
		personalInfoValidator = new PersonalInfoModel.Builder()
				.withCustomerId(customer.getCustomerId())
				.build();
		final int numberOfTriesPerField = 3;
		
		System.out.println("Please Enter The Following: \n");
		if (!acceptStringAsTokenWithAttempts("First Name: ",
				"\nMust be at least two letters",
				numberOfTriesPerField,
				validateFirstName)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts("Last Name: ",
				"\nMust be at least two letters",
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
		if (!acceptStringAsTokenWithAttempts("SSN: ",
				"\nExample, 777-77-7777",
				numberOfTriesPerField,
				validateSSN)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts("Phone Number: (XXX)XXX-XXXX", 
				"\nTen Digit Phone Number With Area Code: Example, (888)333-3333", 
				numberOfTriesPerField, 
				validatePhone)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts("Email: ", 
				"\nExample, john@gmail.com",
				numberOfTriesPerField, 
				validateEmail)) {
			failPersonalInfoForm();
			return;
		}
		if (!acceptStringAsTokenWithAttempts("Beneficiary, Full Name: ", 
				"\nExample, John Doe",
				numberOfTriesPerField, 
				validateBeneficiary)) {
			failPersonalInfoForm();
			return;
		}
		customer.setPersonalInfoModel(personalInfoValidator);
		personalInfoValidator = null;
		System.out.println("\nReady To Submit");
		view = COMPLETE_DETAILS;
	}
	
	private void failApplication() {
		printAbortMessage();
		applicationValidator = null;
		view = MAIN;
	}
	
	private final Validating validateAccountType = ($) -> {
		switch (Util.parseStringAsInt($)) {
		case 1:
			return applicationValidator.setType(AccountType.CHECKING);
		case 2:
			return applicationValidator.setType(AccountType.SAVINGS);
		}
		return false;
	};
	private final Validating validateAccountOwner = ($) -> {
		switch (Util.parseStringAsInt($)) {
		case 2:
			applicationValidator.setJointCustomerId(1l);
		case 1:
			return true;
		} return false;
	};
	private final Validating validateAccountJointCustomerSSN = ($) -> {
		return applicationValidator.setJointCustomerSSN($);
	};
	
	private void applyForAccount() {
		applicationValidator = new ApplicationModel.Builder()
				.withCustomerId(customer.getCustomerId())
				.build();
		final String typePrompt = "Select Account Type\n1\tChecking\n2\tSavings\nType: "; 
		if (!acceptStringAsTokenWithAttempts(typePrompt, 
				"Enter 1 Or 2", 
				2,
				validateAccountType)) {
			failApplication();
			return;
		}
		final String ownerPrompt = "Account Owner\n1\tIndividual\n2\tJoint\nOwner: ";
		if (!acceptStringAsTokenWithAttempts(ownerPrompt, 
				"Enter 1 Or 2", 
				2,
				validateAccountOwner)) {
			failApplication();
			return;
		}
		if (applicationValidator.getJointCustomerId() == 1l) {
			applicationValidator.setJointCustomerId(ApplicationModel.NO_ID);
			
			System.out.println("Enter Joint Holder's SSN: ");
			if (!acceptStringAsTokenWithAttempts("SSN: ", 
					"Example, 123-45-6789", 
					3, validateAccountJointCustomerSSN)) {
				failApplication();
				return;
			}
		}
		
		customer.setApplicationModel(applicationValidator);
		applicationValidator = null;
		System.out.println("\nReady To Submit");
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