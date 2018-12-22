package com.revature.project_0.view;

import com.revature.project_0.entity.Employee;
import java.util.Scanner;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.TableOutcome;

public class EmployeeView extends InputtingContextMenuView implements Operational {
	private Employee employee;
	private int view; 
	
	private final String[] rootOptions = new String[] {
		"Login",
		BACK
	};
	private final String[] mainOptions = new String[] {
			"Manage Applications",
			"View Accounts",
			"View Customer Information",
			BACK
	};
	private final String[] applicationOptions = new String[] {
			"Approve Application",
			"Deny Application",
			BACK
	};
	private final int ROOT 			= 0;
	private final int MAIN 			= 1;
	private final int APPLICATION 	= 2;
	
	private final String NO_MATCHING_RECORDS = "\nNO MATCHING RECORDS";
	private final String NO_ARCHIVED_RECORDS = "NO ARCHIVED RECORDS";
	
	public EmployeeView(Repository repository, Scanner scanner) {
		super(scanner);
		view = ROOT;
		employee = new Employee(repository);
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
				System.out.print("Please Enter Employee Name or ID: ");
				String name = scanner.nextLine();
				employee.setEmployeeId(name);
				view = MAIN;
				break;
			case 2:
				return false;
			} break;
		
		case MAIN:
			switch (choice) {
			case 1:
				String applications = employee.viewAllApplications();
				if (applications.length() > 0)
					System.out.println(applications);
				else
					System.out.println(NO_ARCHIVED_RECORDS);
				view = APPLICATION;
				break;
			case 2:
				String accounts = employee.viewAllAccounts();
				if (accounts.length() > 0)
					System.out.println(accounts);
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
				String recordDump = employee.viewAllAssociatedCustomerInfo(id);
				System.out.println(recordDump.length() == 0 ? NO_MATCHING_RECORDS :
					recordDump);
				break;
			case 4:
				view = ROOT;
				employee.resetEmployeeId();
			} break;
			
		case APPLICATION:
			boolean result;
			long id;
			switch (choice) {
			case 1:
				System.out.print("Please Enter Application ID to Approve: ");
				if (!scanner.hasNextLong()) {
					purgeLine(scanner);
					System.out.println("\nA Valid Application Id Was Not Entered");
					break;
				}
				id = scanner.nextLong();
				purgeLine(scanner);
				result = employee.approveApplication(id, 
						employee.getEmployeeId());
				if (result)
					System.out.println("Success");
				else if (employee.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("No such Application with id = " + id);
				else
					System.out.println("System Error");
				break;
			case 2:
				System.out.print("Please Enter Application ID to Deny: ");
				if (!scanner.hasNextLong()) {
					purgeLine(scanner);
					System.out.println("\nA Valid Application Id Was Not Entered");
					break;
				}
				id = scanner.nextLong();
				purgeLine(scanner);
				result = employee.denyApplication(id, 
						employee.getEmployeeId());
				if (result)
					System.out.println("Success");
				else if (employee.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("\nNo such Application with id = " + id);
				else
					System.out.println(Operational.VISIBLE_SYSTEMS_ERROR);
				break;
			case 3:
				view = MAIN;
			} break;
		}
		return true;
	}

	@Override
	public String provideSalutation() {
		if (employee.getEmployeeId() == null)
			return "(Employee)";
		return new StringBuilder()
				.append("(Employee) ")
			.append(employee.getEmployeeId())
		.toString();
	}
}