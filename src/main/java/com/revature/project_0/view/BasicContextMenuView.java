package com.revature.project_0.view;

import java.util.Scanner;

public abstract class BasicContextMenuView extends ContextMenuView implements Operational{
	Scanner scanner;
	
	public BasicContextMenuView(Scanner scanner) {
		super();
		this.scanner = scanner;
	}

	@Override
	public void goLive() {
		int choice;
		while (true) {
			
			System.out.println("Please enter a number 1 - " + provideCurrentMenu().length);
			displayCurrentMenu();
			if (scanner.hasNextInt()) {
				System.out.println();
				choice = scanner.nextInt();
				purgeLine(scanner);
				if (!consumedChoice(choice))
					break;
			} else
				purgeLine(scanner);
		}
	}
	
	protected void purgeLine(Scanner scanner) {
		scanner.nextLine();
	}
}
