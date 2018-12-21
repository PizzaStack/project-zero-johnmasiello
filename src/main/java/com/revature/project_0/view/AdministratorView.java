package com.revature.project_0.view;

import java.util.Scanner;

import com.revature.project_0.entity.Administrator;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.TableOutcome;

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
	
	private final String NO_MATCHING_RECORDS = "NO MATCHING RECORDS";
	private final String NO_ARCHIVED_RECORDS = "NO ARCHIVED RECORDS";
	
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
		default:
			return nullOptions;
		}
	}

	@Override
	public boolean consumedChoice(int choice) {
		switch (view) {
		case ROOT:
			switch (choice) {
			case 1:
				System.out.print("\nPlease Enter Employee Name or ID: ");
				String name = scanner.nextLine();
				System.out.println();
				administrator.setEmployeeId(name);
				view = MAIN;
				break;
			case 2:
				return false;
			} break;
		
		case MAIN:
			switch (choice) {
			case 1:
				String applications = administrator.viewAllApplications();
				System.out.println("\n");
				if (applications.length() > 0)
					System.out.println(applications);
				else
					System.out.println(NO_ARCHIVED_RECORDS);
				view = APPLICATION;
				break;
			case 2:
				String accounts = administrator.viewAllAccounts();
				System.out.println("\n");
				if (accounts.length() > 0)
					System.out.println(accounts);
				else
					System.out.println(NO_ARCHIVED_RECORDS);
				break;
			case 3:
				System.out.print("\nPlease Enter Customer ID: ");
				if (!scanner.hasNextLong()) {
					purgeLine(scanner);
					System.out.println("\nA Valid Customer Id Was Not Entered\n");
					break;
				}
				long id = scanner.nextLong();
				purgeLine(scanner);
				System.out.println();
				String recordDump = administrator.viewAllAssociatedCustomerInfo(id);
				System.out.println(recordDump.length() == 0 ? NO_MATCHING_RECORDS :
					recordDump);
				System.out.println();
				break;
			case 4:
				view = ROOT;
				administrator.resetEmployeeId();
			} break;
			
		case APPLICATION:
			boolean result;
			long id;
			switch (choice) {
			case 1:
				System.out.print("\nPlease Enter Application ID to Approve: ");
				if (!scanner.hasNextLong()) {
					purgeLine(scanner);
					System.out.println("\nA Valid Application Id Was Not Entered");
					break;
				}
				id = scanner.nextLong();
				purgeLine(scanner);
				System.out.println();
				result = administrator.approveApplication(id, 
						administrator.getEmployeeId());
				if (result)
					System.out.println("Success");
				else if (administrator.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("No such Application with id = " + id);
				else
					System.out.println("System Error");
				System.out.println();
				break;
			case 2:
				System.out.print("\nPlease Enter Application ID to Deny: ");
				if (!scanner.hasNextLong()) {
					purgeLine(scanner);
					System.out.println("\nA Valid Application Id Was Not Entered\n");
					break;
				}
				id = scanner.nextLong();
				purgeLine(scanner);
				System.out.println();
				result = administrator.denyApplication(id, 
						administrator.getEmployeeId());
				if (result)
					System.out.println("Success");
				else if (administrator.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("No such Application with id = " + id);
				else
					System.out.println(Operational.VISIBLE_SYSTEMS_ERROR);
				System.out.println();
				break;
			case 3:
				view = MAIN;
			} break;
		}
		return true;
	}

	@Override
	public String provideSalutation() {
		if (administrator.getEmployeeId() == null)
			return "(Employee)";
		return new StringBuilder()
				.append("(Employee) ")
			.append(administrator.getEmployeeId())
		.toString();
	}
}