package com.revature.project_0.view;

import com.revature.project_0.repository.Repository;

public class CustomerView extends ContextMenuView implements Operational {
	private Repository repository;
	private final String[] rootOptions = new String[] {
			"Do Customer things"
	};
	
	public CustomerView(Repository repository) {
		this.repository = repository;
	}

	@Override
	public void goLive() {
		while (true) {
			displayRootContextMenu();
			
			// input entered
			if (!consumedChoice(4))
				break;
		}
	}

	@Override
	public String[] provideRootOptions() {
		return rootOptions;
	}

	@Override
	public boolean consumedChoice(int choice) {
		// TODO Auto-generated method stub
		return false;
	}
}