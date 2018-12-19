package com.revature.project_0.view;

import java.util.Scanner;

import com.revature.project_0.repository.Repository;

public class ConsoleView extends ContextMenuView implements Operational {
	private CustomerView customerView;
	private EmployeeView employeeView;
	private AdminView adminView;
	private Scanner scanner;
	
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
	public void goLive(Scanner scanner) {
		this.scanner = scanner;
		int choice;
		while (true) {
			displayCurrentMenu();
			if (scanner.hasNextInt()) {
				System.out.println();
				choice = scanner.nextInt();
				purgeScanner(scanner);
				if (!consumedChoice(choice))
					break;
				System.out.println();
			} else {
				purgeScanner(scanner);
				System.out.println("\nPlease enter a number 1 - " + (rootOptions.length + 1));
			}
		}
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