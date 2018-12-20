package com.revature.project_0.view;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ContextMenuView {
	
	public void displayCurrentMenu() {
		String[] commands = provideCurrentMenu();
		StringBuilder strBuilder = new StringBuilder();
		int i = 1;
		for (String command : commands ) {
			strBuilder.append(i++)
			.append(' ')
			.append(command)
			.append("\n");
		}
		strBuilder.append("\n");
		if (provideSalutation() != null) {
			strBuilder.append(provideSalutation())
			.append(", ");
		}
		strBuilder.append("Enter a number: ");
		System.out.print(strBuilder);
	}
	
	protected final String[] nullOptions = new String[0];
	protected final String BACK = "<-Back";
	
	@NotNull
	public abstract String[] provideCurrentMenu();
	
	public abstract boolean consumedChoice(int choice);
	
	@Nullable
	public abstract String provideSalutation();
}
