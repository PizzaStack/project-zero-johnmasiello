package com.revature.project_0.view;

import com.revature.project_0.entity.Customer;
import com.revature.project_0.entity.TransactionOutcome;
import com.revature.project_0.io.Validating;

import java.util.List;
import java.util.Scanner;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.AccountType;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.CustomerFriendlyAccount;
import com.revature.project_0.repository.model.PersonalInfoModel;
import com.revature.project_0.util.Util;

public class CustomerView extends InputtingContextMenuView implements Operational {
	private Customer customer;
	private PersonalInfoModel personalInfoForm;
	private ApplicationModel applicationForm;
	private int view;
	private final String[] rootOptions = new String[] {
			"Login",
			"Sign Up",
			BACK
	};
	private final String[] mainOptions = new String[] {
			"View Accounts",
			"Open New Account",
			"Update Personal Info",
			"Sign Out"
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
				System.out.print("Please Enter Password: ");
				password = scanner.nextLine();
				if (customer.signInSuccessful(name,  password)) {
					System.out.println("\nLogin Successful");
					view = MAIN;
				} else {
					System.out.println("\nLogin Failed");
				}
				break;
			case 2:
				int numberOfTriesLeft = 3;
				while (numberOfTriesLeft-- > 0) {
					System.out.print("Please Enter New Username (Length > 6): ");
					name = scanner.nextLine();
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
					if (customer.isValidPassword(password))
						break;
					System.out.println(String.valueOf(numberOfTriesLeft) +
							" more attempts...\n");
				}
				if (numberOfTriesLeft < 0)
					break;
				if (customer.createNewCustomer(name, password)) {
					System.out.println("\nUser Login Created Successfully");
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
				if (!customer.fetchAccounts(customer.getCustomerId()))
					System.out.println("No Accounts On Record");
				else {
					customer.viewAllAccounts();
					view = TRANSACTION;
				}
				break;
			case 2:
				if (!customer.hasPersonalRecordOnFile()) {
					System.out.println("We appreciate your business");
					System.out.println("We would like to know a little more about you\n");
					fillOutPersonalInformation();
				} else {
					System.out.println("Let's help you apply for a new account\n");
					applyForAccount();
				}
				break;
			case 3:
				System.out.println("Stay Up-To-Date");
				fillOutPersonalInformation();
				break;
			case 4:
				signCustomerOut();
				break;
			}
			break;
			
		case TRANSACTION:
			AccountInfoModel targetAccount;
			List<AccountInfoModel> accounts = customer.getAccounts();
			String enumeratedAccounts = customer.viewAllAcountsAsEnumerated();
			int pickAccount;
			double amount;
			
			switch (choice) {
			case 1:
				System.out.println(enumeratedAccounts);
				System.out.print("Select Account for Deposit: ");
				if (!scanner.hasNextInt()) {
					purgeLine(scanner);
					System.out.println("No Account Selected");
					break;
				}
				pickAccount = scanner.nextInt();
				purgeLine(scanner);
				if (pickAccount > 0 && pickAccount <= accounts.size()) {
					targetAccount = accounts.get(pickAccount - 1);
					
					System.out.print("Enter Amount for Deposit: $");
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
					switch (customer.getFundsTransactionManager().makeDeposit(targetAccount, amount)) {
					case TransactionOutcome.SUCCESS:
						System.out.println("Deposit Made Successfully");
						System.out.println(new CustomerFriendlyAccount(targetAccount));
						break;
					case TransactionOutcome.ACCOUNT_FROZEN:
						System.out.println("Deposit Unsuccessful");
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
				System.out.println(enumeratedAccounts);
				System.out.print("Select Account for Withdrawal: ");
				if (!scanner.hasNextInt()) {
					purgeLine(scanner);
					System.out.println("No Account Selected");
					break;
				}
				pickAccount = scanner.nextInt();
				purgeLine(scanner);
				if (pickAccount > 0 && pickAccount <= accounts.size()) {
					targetAccount = accounts.get(pickAccount - 1);
					
					System.out.print("Enter Amount for Withdrawal: $");
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
					switch (customer.getFundsTransactionManager().makeWithdrawal(targetAccount, amount)) {
					case TransactionOutcome.SUCCESS:
						System.out.println("Withdrawal Made Successfully");
						System.out.println(new CustomerFriendlyAccount(targetAccount));
						break;
					case TransactionOutcome.ACCOUNT_FROZEN:
						System.out.println("Withdrawal Unsuccessful");
						System.out.println("Reason: The Account With Id " 
								+ targetAccount.getAccountId()
								+ " Is Not Currently Active");
						break;
					case TransactionOutcome.INSUFFICIENT_FUNDS:
						System.out.println("Withdrawal Unsuccessful");
						System.out.println("Reason: InSufficient Funds"); 
						break;
					}
					
				} else {
					System.out.println("No Account Selected");
				}
				break;
			case 3:
				System.out.println(enumeratedAccounts);
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
				switch (customer.getFundsTransactionManager().makeTransferOfFunds(originAccount, 
						targetAccount, 
						amount)) {
				case TransactionOutcome.SUCCESS:
					System.out.println("Transfer Made Successfully");
					System.out.println(new CustomerFriendlyAccount(targetAccount));
					System.out.println(new CustomerFriendlyAccount(originAccount));
					break;
				case TransactionOutcome.ACCOUNT_FROZEN:
					System.out.println("Transfer Not Made");
					System.out.println("Reason: The Account With Id " 
							+ originAccount.getAccountId()
							+ " Is Not Currently Active");
					break;
				case TransactionOutcome.RECIPIENT_ACCOUNT_FROZEN:
					System.out.println("Transfer Not Made");
					System.out.println("Reason: The Account With Id " 
							+ targetAccount.getAccountId()
							+ " Is Not Currently Active");
					break;
				case TransactionOutcome.INSUFFICIENT_FUNDS:
					System.out.println("Transfer Not Made");
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
				System.out.println("Please Wait for Your Account to be Created");
				if (!customer.createApplication(customer.getApplication())) {
					System.out.println(Operational.VISIBLE_SYSTEMS_ERROR);
				}
				view = MAIN;
				break;
			case 2:
				System.out.println("Application Canceled by User\n");
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
				System.out.println("Personal Details Canceled by User\n");
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
		personalInfoForm = null;
		view = MAIN;
	}
	
	private final Validating validateFirstName = ($) -> {
		return personalInfoForm.setFirstName($);
	};
	private final Validating validateLastName = ($) -> {
		return personalInfoForm.setLastName($);
	};
	private final Validating validateMI = ($) -> {
		if ($.length() == 1) {
			personalInfoForm.setMiddleInitial($.charAt(0));
			return true;
		}
		return false;
	};
	private final Validating validateSSN = ($) -> {
		return personalInfoForm.setSSN($);
	};
	
	private final Validating validateDob = ($) -> {
		return personalInfoForm.setDob(Util.parseDate($));
	};
	private final Validating validatePhone = ($) -> {
		return personalInfoForm.setPhoneNumber($);
	};
	private final Validating validateEmail= ($) -> {
		return personalInfoForm.setEmail($);
	};
	private final Validating validateBeneficiary = ($) -> {
		return personalInfoForm.setBeneficiary($);
	};
	
	private void fillOutPersonalInformation() {
		personalInfoForm = new PersonalInfoModel.Builder()
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
		if (!acceptStringAsTokenWithAttempts("Phone Number: (XXX)XXX-XXXX: ", 
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
		if (!acceptStringAsTokenWithAttempts("Beneficiary, full name: ", 
				"\nExample, John Doe",
				numberOfTriesPerField, 
				validateBeneficiary)) {
			failPersonalInfoForm();
			return;
		}
		customer.setPersonalInfoModel(personalInfoForm);
		personalInfoForm = null;
		System.out.println("\nReady to Submit");
		view = COMPLETE_DETAILS;
	}
	
	private void failApplication() {
		printAbortMessage();
		applicationForm = null;
		view = MAIN;
	}
	
	private final Validating validateAccountType = ($) -> {
		switch (Util.parseStringAsInt($)) {
		case 1:
			return applicationForm.setType(AccountType.CHECKING);
		case 2:
			return applicationForm.setType(AccountType.SAVINGS);
		}
		return false;
	};
	private final Validating validateAccountOwner = ($) -> {
		switch (Util.parseStringAsInt($)) {
		case 2:
			applicationForm.setJointCustomerId(1l);
		case 1:
			return true;
		} return false;
	};
	private final Validating validateAccountJointCustomerSSN = ($) -> {
		return applicationForm.setJointCustomerSSN($);
	};
	
	private void applyForAccount() {
		applicationForm = new ApplicationModel.Builder()
				.withCustomerId(customer.getCustomerId())
				.build();
		System.out.print("Enter the Name for the Account: ");
		if (!scanner.hasNextLine()) {
			System.out.println("Account Name Not Given");
			return;
		}
		String accountName = scanner.nextLine();
		if (accountName.length() == 0) {
			System.out.println("Not a Valid Account Name");
			return;
		}
		applicationForm.setAccountName(accountName);
		
		if (!acceptStringAsTokenWithAttempts("Select Account Type\n1\tChecking\n2\tSavings\nType: ", 
				"Enter 1 Or 2", 
				2,
				validateAccountType)) {
			failApplication();
			return;
		}
		System.out.println();
		if (!acceptStringAsTokenWithAttempts("Account Owner\n1\tIndividual\n2\tJoint\nOwner: ", 
				"Enter 1 Or 2", 
				2,
				validateAccountOwner)) {
			failApplication();
			return;
		}
		if (applicationForm.getJointCustomerId() == 1l) {
			applicationForm.setJointCustomerId(ApplicationModel.NO_ID);
			
			System.out.println("Enter Joint Holder's SSN: ");
			if (!acceptStringAsTokenWithAttempts("SSN: ", 
					"Example, 123-45-6789", 
					3, validateAccountJointCustomerSSN)) {
				failApplication();
				return;
			}
		}
		
		customer.setApplicationModel(applicationForm);
		applicationForm = null;
		System.out.println("\nReady to Submit");
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