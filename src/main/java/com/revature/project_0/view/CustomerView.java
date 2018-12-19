package com.revature.project_0.view;

import com.revature.project_0.io.WrapperScanner;
import com.revature.project_0.repository.Repository;

public class CustomerView extends ContextMenuView implements Operational {
	private Repository repository;
	private int view;
	private final String[] rootOptions = new String[] {
			"Login",
			"Sign Up"
	};
	
	private final int ROOT 			= 0;
	private final int MAIN 			= 1;
	
	public CustomerView(Repository repository) {
		this.repository = repository;
		view = ROOT;
	}

	@Override
	public void goLive(WrapperScanner scanenr) {
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
				.append("(Member) ")
		.append("Hi")
		.toString();
	}
}