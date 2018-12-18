package com.revature.project_0.view;

import com.revature.project_0.repository.Repository;

public class ConsoleView extends ContextMenuView implements Operational {
	private CustomerView customerView;
	private EmployeeView employeeView;
	private AdminView adminView;
	
	private final String[] rootOptions = new String[] {
			"Customer",
			"Employee",
			"Admin"
	};
	
	public ConsoleView(Repository repository) {
		customerView = new CustomerView(repository);
		employeeView = new EmployeeView(repository);
		adminView = new AdminView(repository);
	}

	@Override
	public void goLive() {
		while (true) {
			displayRootContextMenu();
			
			// input entered
			if (!consumedChoice(2));
				break;
		}
	}

	@Override
	public String[] provideRootOptions() {
		return rootOptions;
	}

	@Override
	public boolean consumedChoice(int choice) {
		switch (choice) {
		case 1:
			customerView.goLive();
			return true;
		case 2:
			employeeView.goLive();
			return true;
		case 3:
			adminView.goLive();
			return true;
		default:
			return false;
		}
	}
}