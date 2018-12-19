package com.revature.project_0.io;

import java.util.Scanner;

public class WrapperScanner {
	private Scanner scanner;

	public WrapperScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public boolean hasNextLine() {
		return scanner.hasNextLine();
	}
	
	public String nextLine() {
		return scanner.nextLine();
	}
	
	public void purgeLine() {
		scanner.nextLine();
	}
	
	public boolean hasNextLong() {
		return scanner.hasNextLong();
	}
	
	public long nextLong() {
		return scanner.nextLong();
	}
	
	public boolean hasNextInt() {
		return scanner.hasNextInt();
	}
	
	public int nextInt() {
		return scanner.nextInt();
	}
	
	public boolean hasNext() {
		return scanner.hasNext();
	}
	
	public String next() {
		return scanner.next();
	}
}
