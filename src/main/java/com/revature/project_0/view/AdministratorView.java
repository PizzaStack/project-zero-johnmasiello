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
	private final String[] applicationOptions = new String[] {
			"Approve Application",
			"Deny Application",
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
			BACK
	};
	private final String[] accountApprovalOptions = new String[] {
			"Approve Account",
			"Deny Account",
			BACK
	};
	private final int ROOT 					= 0;
	private final int MAIN 					= 1;
	private final int APPLICATION 			= 2;
	private final int ACCOUNT	 			= 3;
	private final int ACCOUNT_TRANSACTION 	= 4;
	private final int ACCOUNT_APPROVAL		= 5;
	
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
				if (accounts.length() > 0) {
					System.out.println(accounts);
					view = ACCOUNT;
				}
				else
					System.out.println(NO_ARCHIVED_RECORDS);
				break;
			case 3:
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
			case 4:
				view = ROOT;
				administrator.resetEmployeeId();
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
				result = administrator.approveApplication(id, 
						administrator.getEmployeeId());
				if (result)
					System.out.println("Success");
				else if (administrator.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("No such Application with id = " + id);
				else
					System.out.println("System Error");
				break;
			case 2:
				id = promptIdFromUser("Please Enter Application ID to Deny: ", 
						"\nA Valid Application Id Was Not Entered");
				if (id == null)
					break;
				result = administrator.denyApplication(id, 
						administrator.getEmployeeId());
				if (result)
					System.out.println("Success");
				else if (administrator.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("No such Application with id = " + id);
				else
					System.out.println(Operational.VISIBLE_SYSTEMS_ERROR);
				break;
			case 3:
				view = MAIN;
			}
		} 
		break;
			
		case ACCOUNT:
			switch (choice) {
			case 1:
				view = ACCOUNT_TRANSACTION;
				break;
			case 2:
				view = ACCOUNT_APPROVAL;
				break;
			case 3:
				String accounts = administrator.viewAllAccounts();
				System.out.println(accounts + "\n");
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
			String accounts = administrator.viewAllAccounts();
			System.out.println(accounts + "\n");
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
			System.out.println(administrator.viewAllAccounts());
			
			switch (choice) {
			case 1:
				System.out.print("Select Account for Deposit: ");
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
					System.out.println(targetAccount.provideGlimpse());
					break;
				case TransactionOutcome.ACCOUNT_FROZEN:
					System.out.println("The Account with Id=" +
							+ targetAccount.getAccountId()
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
						targetAccount.setAdminId(administrator.getEmployeeId());
						if (administrator.getFundsTransactionManager()
								.makeDeposit(targetAccount, amount)
								== TransactionOutcome.SUCCESS)
							System.out.println("Deposit Made Successfully");	
						else
							System.out.println("Deposit Unsuccessful");
					}
					break;
				} 
				break;
			case 2:
				System.out.print("Select Account for Withdrawal: ");
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
					System.out.println(targetAccount.provideGlimpse());
					break;
				case TransactionOutcome.INSUFFICIENT_FUNDS:
					System.out.println("Withdrawal Unsuccessful");
					System.out.println("Reason: InSufficient Funds"); 
					break;
				case TransactionOutcome.ACCOUNT_FROZEN:
					System.out.println("The Account with Id=" +
							+ targetAccount.getAccountId()
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
						targetAccount.setAdminId(administrator.getEmployeeId());
						if (administrator.getFundsTransactionManager()
								.makeWithdrawal(targetAccount, amount)
								== TransactionOutcome.SUCCESS)
							System.out.println("Withdrawal Made Successfully");	
						else
							System.out.println("Withdrawal Unsuccessful");
					}
					break;
				} 
				break;
			case 3:
				targetId = promptIdFromUser("Transfer Funds Into Account: ", 
						"\nA Valid Account Id Was Not Entered");
				if (targetId == null)
					break;
				targetAccount = administrator.getAccountById(targetId);
				if (targetAccount == null) {
					System.out.println("No Account Selected");
					break;
				}
				originId = promptIdFromUser("From Account: ", 
						"\nA Valid Account Id Was Not Entered");
				if (originId == null)
					break;
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
					System.out.println(targetAccount.provideGlimpse());
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
								+ singularlyFrozenAccount.getAccountId()
								+ " is Frozen");
						System.out.print("Proceed with Unlocking Account? (y|n) ");
					} else {
						System.out.println("Accounts with Id's="
								+ targetAccount.getAccountId()
								+ ", " + originAccount.getAccountId()
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
							singularlyFrozenAccount.setAdminId(administrator.getEmployeeId());
						} else {
							targetAccount.setStatus(AccountStatus.APPROVED);
							targetAccount.setAdminId(administrator.getEmployeeId());
							originAccount.setStatus(AccountStatus.APPROVED);
							originAccount.setAdminId(administrator.getEmployeeId());
						}
						if (administrator.getFundsTransactionManager()
								.makeTransferOfFunds(
										originAccount, 
										targetAccount, 
										amount)
								== TransactionOutcome.SUCCESS)
							System.out.println("Transfer Made Successfully");	
						else
							System.out.println("Transfer Unsuccessful");
					}
					break;
				} 
				break;
			case 4:
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