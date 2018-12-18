package com.revature.project_0.view;

public abstract class ContextMenuView {
	public void displayContextMenuWithPrompt(String[] commands) {
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
		.append("\nEnter a number: ");
		System.out.print(strBuilder);
	}
	
	public void displayRootContextMenu() {
		displayContextMenuWithPrompt(provideRootOptions());
		System.out.println();
	}
	
	public abstract String[] provideRootOptions();
	
	public abstract boolean consumedChoice(int choice);
}
