package com.revature.project_0.view;

import java.util.Scanner;
import com.revature.project_0.repository.Repository;

public class ConsoleView extends BasicContextMenuView implements Operational {
	private CustomerView customerView;
	private EmployeeView employeeView;
	private AdministratorView adminView;
	
	private final String[] rootOptions = new String[] {
			"Customer",
			"Employee",
			"Admin",
			BACK
	};
	
	public ConsoleView(Repository repository, Scanner scanner) {
		super(scanner);
		customerView = new CustomerView(repository, scanner);
		employeeView = new EmployeeView(repository, scanner);
		adminView = new AdministratorView(repository, scanner);
	}

	@Override
	public String[] provideCurrentMenu() {
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
		case 4:
			return false;
		default:
				return true;
		}
	}
	
	@Override
	public String provideSalutation() {
		return null;
	}
}