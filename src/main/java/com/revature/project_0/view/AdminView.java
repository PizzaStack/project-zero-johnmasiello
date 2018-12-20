package com.revature.project_0.view;

import java.util.Scanner;
import com.revature.project_0.repository.Repository;

public class AdminView extends BasicContextMenuView implements Operational {
	private Repository repository;
	private final String[] rootOptions = new String[] {
			"Do Admin things"
	};
	
	public AdminView(Repository repository, Scanner scanner) {
		super(scanner);
		this.repository = repository;
	}

	@Override
	public String[] provideCurrentMenu() {
		return rootOptions;
	}

	@Override
	public boolean consumedChoice(int choice) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String provideSalutation() {
		return new StringBuilder()
				.append("(Admin) ")
		.append("Hello")
		.toString();
	}
}
