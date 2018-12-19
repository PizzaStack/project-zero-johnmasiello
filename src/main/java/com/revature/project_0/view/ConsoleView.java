package com.revature.project_0.view;

import com.revature.project_0.repository.Repository;

public class ConsoleView extends BasicContextMenuView implements Operational {
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
	public String[] provideCurrentMenu() {
		return rootOptions;
	}

	@Override
	public boolean consumedChoice(int choice) {
		switch (choice) {
		case 1:
			customerView.goLive(scanner);
			return true;
		case 2:
			employeeView.goLive(scanner);
			return true;
		case 3:
			adminView.goLive(scanner);
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public String provideSalutation() {
		return null;
	}
}