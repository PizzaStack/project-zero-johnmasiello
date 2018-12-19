package com.revature.project_0.view;

import com.revature.project_0.entity.Employee;
import com.revature.project_0.entity.actions.EmployeeManageApplication;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.TableOutcome;

public class EmployeeView extends BasicContextMenuView implements Operational {
	private Employee employee;
	private int view; 
	
	private final String[] rootOptions = new String[] {
		"Login"	
	};
	private final String[] mainOptions = new String[] {
			"View/Approve Applications",
			"View Accounts",
			"View Customer Information"
	};
	private final String[] applicationOptions = new String[] {
			"Approve Application",
			"Deny Application"
	};
	private final int ROOT 			= 0;
	private final int MAIN 			= 1;
	private final int APPLICATION 	= 2;
	
	private final String NO_MATCHING_RECORDS = "NO MATCHING RECORDS";
	private final String NO_ARCHIVED_RECORDS = "NO ARCHIVED RECORDS";
	
	public EmployeeView(Repository repository) {
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
				System.out.print("\nPlease Enter Employee Name or ID: ");
				String name = scanner.nextLine();
				System.out.println();
				employee.getSelfIdentify().setEmployeeId(name);
				view = MAIN;
				break;
			default:
				return false;
			} break;
		
		case MAIN:
			switch (choice) {
			case 1:
				employee.getViewInfos().viewAllApplications();
				System.out.println(NO_ARCHIVED_RECORDS);
				System.out.println();
				view = APPLICATION;
				break;
			case 2:
				employee.getViewInfos().viewAllAccounts();
				System.out.println(NO_ARCHIVED_RECORDS);
				System.out.println();
				break;
			case 3:
				System.out.print("\nPlease Enter Customer ID: ");
				if (!scanner.hasNextLong()) {
					scanner.purgeLine();
					System.out.println("\nA Valid Customer Id Was Not Entered\n");
					break;
				}
				long id = scanner.nextLong();
				scanner.purgeLine();
				System.out.println();
				String recordDump = employee.getViewInfos().viewAllAssociatedCustomerInfo(id);
				System.out.println(recordDump.length() == 0 ? NO_MATCHING_RECORDS :
					recordDump);
				System.out.println();
				break;
			default:
				view = ROOT;
			} break;
		case APPLICATION:
			EmployeeManageApplication e = employee.getManageApplication();
			boolean result;
			long id;
			switch (choice) {
			case 1:
				System.out.print("\nPlease Enter Application ID to Approve: ");
				if (!scanner.hasNextLong()) {
					scanner.purgeLine();
					System.out.println("\nA Valid Application Id Was Not Entered");
					break;
				}
				id = scanner.nextLong();
				scanner.purgeLine();
				System.out.println();
				result = e.approveApplication(id, 
						employee.getSelfIdentify().getEmployeeId());
				if (result)
					System.out.println("Success");
				else if (e.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("No such Application with id = " + id);
				else
					System.out.println("System Error");
				System.out.println();
				break;
			case 2:
				System.out.print("\nPlease Enter Application ID to Deny: ");
				if (!scanner.hasNextLong()) {
					scanner.purgeLine();
					System.out.println("\nA Valid Application Id Was Not Entered\n");
					break;
				}
				id = scanner.nextLong();
				scanner.purgeLine();
				System.out.println();
				result = e.denyApplication(id, 
						employee.getSelfIdentify().getEmployeeId());
				if (result)
					System.out.println("Success");
				else if (e.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("No such Application with id = " + id);
				else
					System.out.println("System Error");
				System.out.println();
				break;
			default:
				view = MAIN;
			} break;
		default:
			return false;
		}
		return true;
	}

	@Override
	public String provideSalutation() {
		if (employee.getSelfIdentify().getEmployeeId() == null)
			return "(Employee)";
		return new StringBuilder()
				.append("(Employee) ")
			.append(employee.getSelfIdentify().getEmployeeId())
		.toString();
	}
}