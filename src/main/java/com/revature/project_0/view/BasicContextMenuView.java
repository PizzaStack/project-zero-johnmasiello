package com.revature.project_0.view;

import com.revature.project_0.io.WrapperScanner;

public abstract class BasicContextMenuView extends ContextMenuView implements Operational{
	protected WrapperScanner scanner;
	
	@Override
	public void goLive(WrapperScanner scanner) {
		this.scanner = scanner;
		int choice;
		while (true) {
			displayCurrentMenu();
			if (scanner.hasNextInt()) {
				System.out.println();
				choice = scanner.nextInt();
				scanner.purgeLine();
				if (!consumedChoice(choice))
					break;
				System.out.println();
			} else {
				scanner.purgeLine();
				System.out.println("\nPlease enter a number 1 - " + (provideCurrentMenu().length + 1));
			}
		}
	}
}
