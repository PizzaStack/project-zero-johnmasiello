package com.revature.project_0.view;

import java.util.Scanner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.revature.project_0.entity.Administrator;
import com.revature.project_0.entity.TransactionOutcome;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.TableOutcome;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.AccountStatus;
import com.revature.project_0.repository.model.CustomerFriendlyAccount;
import com.revature.project_0.util.Util;

public class AdministratorView extends InputtingContextMenuView implements Operational {
	private Administrator administrator;
	private int view; 
	
	private final String[] rootOptions = new String[] {
			"Login",
			BACK
	};
	private final String[] mainOptions = new String[] {
			"Manage Applications",
			"Manage Accounts",
			"Manage Customer Information",
			BACK
	};
	private final String[] customerInfoOptions = new String[] {
			"View All",
			"View By Customer",
			BACK
	};
	private final String[] applicationOptions = new String[] {
			"Approve Application",
			"Deny Application",
			"Refresh View",
			BACK
	};
	private final String[] accountOptions = new String[] {
			"Manage Transactions",
			"Approve Accounts",
			"Cancel Accounts",
			BACK
	};
	private final String[] transactionOptions = new String[] {
			"Deposit Funds",
			"Withdrawal Funds",
			"Transfer Funds",
			"Refresh Accounts",
			BACK
	};
	private final String[] accountApprovalOptions = new String[] {
			"Approve Account",
			"Deny Account",
			BACK
	};
	private final int ROOT 					= 0;
	private final int MAIN 					= 1;
	private final int CUSTOMER				= 2;
	private final int APPLICATION 			= 3;
	private final int ACCOUNT	 			= 4;
	private final int ACCOUNT_TRANSACTION 	= 5;
	private final int ACCOUNT_APPROVAL		= 6;
	
	private final String NO_MATCHING_RECORDS = "\nNo MATCHING RECORDS";
	private final String NO_ARCHIVED_RECORDS = "No ARCHIVED RECORDS";
	
	public AdministratorView(Repository repository, Scanner scanner) {
		super(scanner);
		view = ROOT;
		administrator = new Administrator(repository);
	}

	@Override
	public String[] provideCurrentMenu() {
		switch (view) {
		case ROOT:
			return rootOptions;
		case MAIN:
			return mainOptions;
		case CUSTOMER:
			return customerInfoOptions;
		case APPLICATION:
			return applicationOptions;
		case ACCOUNT:
			return accountOptions;
		case ACCOUNT_TRANSACTION:
			return transactionOptions;
		case ACCOUNT_APPROVAL:
			return accountApprovalOptions;
		default:
			return nullOptions;
		}
	}

	@Nullable
	private Long promptIdFromUser(@NotNull String prompt, @NotNull String badIdMessage ) {
		Long id = null;
		System.out.print(prompt);
		if (!scanner.hasNextLong()) {
			purgeLine(scanner);
			System.out.println(badIdMessage);
			return id;
		}
		id = scanner.nextLong();
		purgeLine(scanner);
		System.out.println();
		return id;
	}
	
	private void viewAllAccounts() {
		String accounts = administrator.viewAllAccounts();
		if (accounts.length() > 0)
			System.out.println(accounts);
		else
			System.out.println(NO_MATCHING_RECORDS);
	}
	
	@Override
	public boolean consumedChoice(int choice) {
		switch (view) {
		case ROOT:
			switch (choice) {
			case 1:
				System.out.print("Please Enter Employee Name or ID: ");
				String name = scanner.nextLine();
				administrator.setEmployeeId(name);
				view = MAIN;
				break;
			case 2:
				return false;
			} 
			break;
		
		case MAIN:
			switch (choice) {
			case 1:
				String applications = administrator.viewAllApplications();
				if (applications.length() > 0)
					System.out.println(applications);
				else
					System.out.println(NO_ARCHIVED_RECORDS);
				view = APPLICATION;
				break;
			case 2:
				String accounts = administrator.viewAllAccounts();
				if (accounts.length() > 0)
					System.out.println(accounts);
				else
					System.out.println(NO_ARCHIVED_RECORDS);
				view = ACCOUNT;
				break;
			case 3:{
				String records = administrator.viewAllCustomersPersonalInformation();
				if (records.length() > 0)
					System.out.println(records);
				else
					System.out.println(NO_ARCHIVED_RECORDS);
				view = CUSTOMER;
			}
			break;
			case 4:
				view = ROOT;
				administrator.resetEmployeeId();
			} 
			break;
			
		case CUSTOMER:
			switch (choice) {
			case 1: {
				String records = administrator.viewAllCustomersPersonalInformation();
				if (records.length() > 0)
					System.out.println(records);
				else
					System.out.println(NO_ARCHIVED_RECORDS);	
			}
			break;
			case 2:
				System.out.print("Please Enter Customer ID: ");
				if (!scanner.hasNextLong()) {
					purgeLine(scanner);
					System.out.println("\nA Valid Customer Id Was Not Entered");
					break;
				}
				long id = scanner.nextLong();
				purgeLine(scanner);
				String recordDump = administrator.viewAllAssociatedCustomerInfo(id);
				System.out.println(recordDump.length() == 0 ? NO_MATCHING_RECORDS :
					recordDump);
				break;
			case 3:
				view = MAIN;
				break;
			}
			break;
			
		case APPLICATION: {
			boolean result;
			Long id;
			switch (choice) {
			case 1:
				id = promptIdFromUser("Please Enter Application ID to Approve: ", 
						"\nA Valid Application Id Was Not Entered");
				if (id == null)
					break;
				administrator.approveApplication(id, 
						administrator.getEmployeeId());
				switch (administrator.getErrorCode()) {
				case TableOutcome.OK:
					System.out.println("Success, New Account Created: ");
					System.out.println(administrator.getNewAccount());
					break;
				case TableOutcome.JOINT_CUSTOMER_NOT_FOUND:
					System.out.println("Unable to Create Joint Account");
					System.out.println("Reason: Joint Customer Not Found");
					break;
				case TableOutcome.NO_SUCH_RECORD:
					System.out.println("No such Application with id = " + id);
					break;
				case TableOutcome.FAIL_TO_UPDATE:
					System.out.println("System Error");
					break;
				}
				break;
			case 2:
				id = promptIdFromUser("Please Enter Application ID to Deny: ", 
						"\nA Valid Application Id Was Not Entered");
				if (id == null)
					break;
				result = administrator.denyApplication(id, 
						administrator.getEmployeeId());
				if (result) {
					System.out.println("Success, Application Denied");
				}
				else if (administrator.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("No such Application with id = " + id);
				else
					System.out.println(Operational.VISIBLE_SYSTEMS_ERROR);
				break;
			case 3:
				String applications = administrator.viewAllApplications();
				if (applications.length() > 0)
					System.out.println(applications);
				else
					System.out.println(NO_ARCHIVED_RECORDS);
				break;
			case 4:
				view = MAIN;
				break;
			}
		} 
		break;
			
		case ACCOUNT:
			switch (choice) {
			case 1:
				viewAllAccounts();
				view = ACCOUNT_TRANSACTION;
				break;
			case 2:
				viewAllAccounts();
				view = ACCOUNT_APPROVAL;
				break;
			case 3:
				viewAllAccounts();
				Long id = null;
				AccountInfoModel result;
				id = promptIdFromUser("Please Enter Account ID to Cancel: ", 
						"\nA Valid Account Id Was Not Entered");
				if (id == null)
					break;
				
				result = administrator.cancelAccount(id);
				if (result != null) {
					System.out.println(result);
					System.out.println("Account Canceled");
				} else {
					System.out.println("No such Account with id = " + id);
				}
				break;
			case 4:
				view = MAIN;
				break;
			} 
			break;
			
		case ACCOUNT_APPROVAL:
		{
			Long id = null;
			AccountInfoModel result;
			switch (choice) {
			case 1:
				id = promptIdFromUser("Please Enter Account ID to Approve: ", 
						"\nA Valid Account Id Was Not Entered");
				if (id == null)
					break;
				
				result = administrator.approveAccount(id);
				if (result != null) {
					System.out.println(result);
					System.out.println("Account Approved");
				} else {
					System.out.println("No such Account with id = " + id);
				}
				break;
			case 2:
				id = promptIdFromUser("Please Enter Account ID to Deny: ", 
						"\nA Valid Account Id Was Not Entered");
				if (id == null)
					break;
				
				result = administrator.denyAccount(id);
				if (result != null) {
					System.out.println(result);
					System.out.println("Account Denied");
				} else {
					System.out.println("No such Account with id = " + id);
				}
				break;
			case 3:
				view = ACCOUNT;
				break;
			}
		}
		break;
			
		case ACCOUNT_TRANSACTION:
			AccountInfoModel targetAccount, originAccount;
			Long targetId, originId;
			double amount;
			String strConfirm;
			
			switch (choice) {
			case 1:
				targetId = promptIdFromUser("Please Enter Account ID for Deposit: ", 
						"\nA Valid Account Id Was Not Entered");
				if (targetId == null)
					break;
				targetAccount = administrator.getAccountById(targetId);
				if (targetAccount == null) {
					System.out.println("No Account Selected");
					break;
				}
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
				switch (administrator.getFundsTransactionManager()
						.makeDeposit(
								targetAccount, 
								amount)) {
				case TransactionOutcome.SUCCESS:
					System.out.println("Deposit Made Successfully");
					System.out.println(new CustomerFriendlyAccount(targetAccount));
					break;
				case TransactionOutcome.ACCOUNT_FROZEN:
					System.out.println("The Account with Id=" 
							+ Util.zeroPadId(targetAccount.getAccountId())
							+ " is Frozen");
					System.out.print("Proceed with Unlocking Account? (y|n) ");
					if (!scanner.hasNext()) {
						System.out.println("Deposit Unsuccessful");
						purgeLine(scanner);
						break;
					}
					strConfirm = scanner.next();
					purgeLine(scanner);
					if (strConfirm.length() != 1 || 
							Character.toUpperCase(strConfirm.charAt(0)) != 'Y')
						System.out.println("Deposit Unsuccessful");
					else {
						targetAccount.setStatus(AccountStatus.APPROVED);
						targetAccount.setAcctApproverId(administrator.getEmployeeId());
						if (administrator.getFundsTransactionManager()
								.makeDeposit(targetAccount, amount)
								== TransactionOutcome.SUCCESS) {
							System.out.println("Deposit Made Successfully");
							System.out.println(new CustomerFriendlyAccount(targetAccount));
						}
						else
							System.out.println("Deposit Unsuccessful");
					}
					break;
				} 
				break;
			case 2:
				targetId = promptIdFromUser("Please Enter Account ID for Withdrawal: ", 
						"\nA Valid Account Id Was Not Entered");
				if (targetId == null)
					break;
				targetAccount = administrator.getAccountById(targetId);
				if (targetAccount == null) {
					System.out.println("No Account Selected");
					break;
				}
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
				switch (administrator.getFundsTransactionManager()
						.makeWithdrawal(
								targetAccount, 
								amount)) {
				case TransactionOutcome.SUCCESS:
					System.out.println("Withdrawal Made Successfully");
					System.out.println(new CustomerFriendlyAccount(targetAccount));
					break;
				case TransactionOutcome.INSUFFICIENT_FUNDS:
					System.out.println("Withdrawal Unsuccessful");
					System.out.println("Reason: InSufficient Funds"); 
					break;
				case TransactionOutcome.ACCOUNT_FROZEN:
					System.out.println("The Account with Id=" 
							+ Util.zeroPadId(targetAccount.getAccountId())
							+ " is Frozen");
					System.out.print("Proceed with Unlocking Account? (y|n) ");
					if (!scanner.hasNext()) {
						System.out.println("Withdrawal Unsuccessful");
						purgeLine(scanner);
						break;
					}
					strConfirm = scanner.next();
					purgeLine(scanner);
					if (strConfirm.length() != 1 || 
							Character.toUpperCase(strConfirm.charAt(0)) != 'Y')
						System.out.println("Withdrawal Unsuccessful");
					else {
						targetAccount.setStatus(AccountStatus.APPROVED);
						targetAccount.setAcctApproverId(administrator.getEmployeeId());
						if (administrator.getFundsTransactionManager()
								.makeWithdrawal(targetAccount, amount)
								== TransactionOutcome.SUCCESS) {
							System.out.println("Withdrawal Made Successfully");
							System.out.println(new CustomerFriendlyAccount(targetAccount));
						}
						else
							System.out.println("Withdrawal Unsuccessful");
					}
					break;
				} 
				break;
			case 3:
				targetId = promptIdFromUser("Transfer Funds Into Account with ID: ", 
						"\nA Valid Account Id Was Not Entered");
				if (targetId == null)
					break;
				targetAccount = administrator.getAccountById(targetId);
				if (targetAccount == null) {
					System.out.println("No Account Selected");
					break;
				}
				originId = promptIdFromUser("From Account with ID: ", 
						"\nA Valid Account Id Was Not Entered");
				if (originId == null)
					break;
				if (originId == targetId ) {
					System.out.println("\nAccounts cannot be the same");
					break;
				}
				originAccount = administrator.getAccountById(originId);
				if (originAccount == null) {
					System.out.println("No Account Selected");
					break;
				}
				System.out.print("Enter Amount for Transfer: $");
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
				switch (administrator.getFundsTransactionManager()
						.makeTransferOfFunds(
								originAccount, 
								targetAccount, 
								amount)) {
				case TransactionOutcome.SUCCESS:
					System.out.println("Transfer Made Successfully");
					System.out.println(new CustomerFriendlyAccount(targetAccount));
					System.out.println(new CustomerFriendlyAccount(originAccount));
					break;
				case TransactionOutcome.INSUFFICIENT_FUNDS:
					System.out.println("Transfer Unsuccessful");
					System.out.println("Reason: InSufficient Funds"); 
					break;
				case TransactionOutcome.ACCOUNT_FROZEN:
				case TransactionOutcome.RECIPIENT_ACCOUNT_FROZEN:
					AccountInfoModel singularlyFrozenAccount = 
						originAccount.isAccountReadyForTransactions() ? targetAccount
								: targetAccount.isAccountReadyForTransactions() ? originAccount
										: null;
					if (singularlyFrozenAccount != null) {
						System.out.println("The Account with Id="
								+ Util.zeroPadId(singularlyFrozenAccount.getAccountId())
								+ " is Frozen");
						System.out.print("Proceed with Unlocking Account? (y|n) ");
					} else {
						System.out.println("Accounts with Id's="
								+ Util.zeroPadId(targetAccount.getAccountId())
								+ ", " + Util.zeroPadId(originAccount.getAccountId())
								+ " are frozen");
						System.out.print("Proceed with Unlocking Both Accounts? (y|n) ");
					}
					if (!scanner.hasNext()) {
						System.out.println("Transfer Unsuccessful");
						purgeLine(scanner);
						break;
					}
					strConfirm = scanner.next();
					purgeLine(scanner);
					if (strConfirm.length() != 1 || 
							Character.toUpperCase(strConfirm.charAt(0)) != 'Y')
						System.out.println("Transfer Unsuccessful");
					else {
						if (singularlyFrozenAccount != null) {
							singularlyFrozenAccount.setStatus(AccountStatus.APPROVED);
							singularlyFrozenAccount.setAcctApproverId(administrator.getEmployeeId());
						} else {
							targetAccount.setStatus(AccountStatus.APPROVED);
							targetAccount.setAcctApproverId(administrator.getEmployeeId());
							originAccount.setStatus(AccountStatus.APPROVED);
							originAccount.setAcctApproverId(administrator.getEmployeeId());
						}
						if (administrator.getFundsTransactionManager()
								.makeTransferOfFunds(
										originAccount, 
										targetAccount, 
										amount)
								== TransactionOutcome.SUCCESS) {
							System.out.println("Transfer Made Successfully");
							System.out.println(new CustomerFriendlyAccount(targetAccount));
							System.out.println(new CustomerFriendlyAccount(originAccount));
						}
						else
							System.out.println("Transfer Unsuccessful");
					}
					break;
				} 
				break;
			case 4:
				System.out.println(administrator.viewAllAccounts());
				break;
			case 5:
				view = MAIN;
				break;
			} 
			break;
		}
		return true;
	}

	@Override
	public String provideSalutation() {
		if (administrator.getEmployeeId() == null)
			return "(Admin)";
		return new StringBuilder()
				.append("(Admin) ")
			.append(administrator.getEmployeeId())
		.toString();
	}
}