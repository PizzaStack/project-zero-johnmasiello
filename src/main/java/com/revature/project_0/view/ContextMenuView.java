package com.revature.project_0.view;

import java.util.Scanner;

import org.jetbrains.annotations.Nullable;

public abstract class ContextMenuView {
	public void displayCurrentMenu() {
		String[] commands = provideCurrentMenu();
		int i = 1;
		StringBuilder strBuilder = new StringBuilder();
		for (String command : commands) {
			strBuilder.append(i++)
			.append(' ')
			.append(command)
			.append("\t");
		}
		strBuilder.append(i)
		.append(' ')
		.append("<-Back")
		.append("\n");
		if (provideSalutation() != null) {
			strBuilder.append(provideSalutation())
			.append(", ");
		}
		strBuilder.append("Enter a number: ");
		System.out.print(strBuilder);
	}
	
	protected void purgeScanner(Scanner scanner) {
		scanner.nextLine();
	}
	
	public abstract String[] provideCurrentMenu();
	
	public abstract boolean consumedChoice(int choice);
	
	@Nullable
	public abstract String provideSalutation();
}
