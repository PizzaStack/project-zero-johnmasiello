package com.revature.project_0.view;

import java.util.Scanner;

import com.revature.project_0.repository.Repository;

public class AdminView extends ContextMenuView implements Operational {
	private Repository repository;
	private final String[] rootOptions = new String[] {
			"Do Admin things"
	};
	
	public AdminView(Repository repository) {
		this.repository = repository;
	}
	
	@Override
	public void goLive(Scanner scanenr) {
		while (true) {
			displayCurrentMenu();
			
			// input entered
			if (!consumedChoice(4))
				break;
		}
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
