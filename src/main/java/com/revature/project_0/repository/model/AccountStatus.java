package com.revature.project_0.repository.model;

public class AccountStatus {
	public static final int OPENED	 = 0;
	public static final int APPROVED = 1;
	public static final int DENIED	 = 2; // TODO accounts are frozen when denied; they should also have balance of 0
	public static final int CLOSED	 = 3;
}