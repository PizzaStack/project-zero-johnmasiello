package com.revature.project_0.view;

import java.util.Scanner;

import com.revature.project_0.io.DataInputting;

public abstract class InputtingContextMenuView extends BasicContextMenuView implements DataInputting {

	public InputtingContextMenuView(Scanner scanner) {
		super(scanner);
	}

	@Override
	public boolean acceptStringAsTokenWithAttempts(String fieldLabel, String showCondition, final int numberOfAttempts,
			Validating validator) {
		
		String strIn;
		int numberOfTries;
		
		System.out.print(fieldLabel);
		numberOfTries = numberOfAttempts;
		strIn = scanner.next();
		purgeLine(scanner);
		while (!validator.validate(strIn) && --numberOfTries > 0) {
			System.out.println(showCondition + " (" + numberOfTries  + " more tries/try)");
			System.out.print(fieldLabel);
			strIn = scanner.next();
			purgeLine(scanner);
		}
		return numberOfTries > 0;
	}
}
