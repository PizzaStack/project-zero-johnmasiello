package com.revature.project_0.view;

import java.util.Scanner;

import com.revature.project_0.entity.Employee;
import com.revature.project_0.entity.actions.EmployeeManageApplication;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.TableOutcome;

public class EmployeeView extends ContextMenuView implements Operational {
	private Employee employee;
	private int view; 
	private final String[] nullOptions = new String[0];
	private final String[] rootOptions = new String[] {
		"Employee Login"	
	};
	private final String[] mainOptions = new String[] {
			"View All Applications",
			"View All Accounts",
			"View Customer Information"
	};
	private final String[] applicationOptions = new String[] {
			"Approve Application",
			"Deny Application"
	};
	private final int ROOT 			= 0;
	private final int MAIN 			= 1;
	private final int APPLICATION 	= 2;
	
	public EmployeeView(Repository repository) {
		view = ROOT;
		employee = new Employee(repository);
	}

	@Override
	public void goLive(Scanner scanner) {
		// HACK
		view = APPLICATION;
		while (true) {
			displayContextMenuWithPrompt(contextOptions());
			
			// input entered
			if (!consumedChoice(1));
				break;
		}
	}

	@Override
	public String[] provideRootOptions() {
		return rootOptions;
	}

	@Override
	public boolean consumedChoice(int choice) {
		switch (view) {
		case ROOT:
			switch (choice) {
			case 1:
				System.out.print("\nPlease Enter Employee Name or ID: ");
				System.out.println("John");
				employee.getSelfIdentify().setEmployeeId("John");
				view = MAIN;
				break;
			default:
				return false;
			} break;
		
		case MAIN:
			switch (choice) {
			case 1:
				employee.getViewInfos().viewAllApplications();
				break;
			case 2:
				employee.getViewInfos().viewAllAccounts();
				break;
			case 3:
				System.out.print("\nPlease Enter Customer ID: ");
				System.out.println(55);
				System.out.println(employee.getViewInfos().viewAllAssociatedCustomerInfo(55));
				break;
			default:
				view = ROOT;
			} break;
		case APPLICATION:
			EmployeeManageApplication e = employee.getManageApplication();
			boolean result;
			long appId;
			switch (choice) {
			case 1:
				System.out.print("\nPlease Enter Application ID to Approve: ");
				appId = 777l;
				System.out.println(appId);
				result = e.approveApplication(appId, 
						employee.getSelfIdentify().getEmployeeId());
				if (result)
					System.out.println("\tSuccess");
				else if (e.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("\tNo such Application with id = " + appId);
				else
					System.out.println("\tSystem Error");
				break;
			case 2:
				System.out.print("\nPlease Enter Application ID to Deny: ");
				appId = 666l;
				System.out.println(appId);
				result = e.denyApplication(appId, 
						employee.getSelfIdentify().getEmployeeId());
				if (result)
					System.out.println("\tSuccess");
				else if (e.getErrorCode() == TableOutcome.NO_SUCH_RECORD)
					System.out.println("\tNo such Application with id = " + appId);
				else
					System.out.println("\tSystem Error");
				break;
			default:
				view = MAIN;
			} break;
		default:
			return false;
		}
		return true;
	}
	
	private String[] contextOptions() {
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
}