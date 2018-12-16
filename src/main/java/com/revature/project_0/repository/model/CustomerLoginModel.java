package com.revature.project_0.repository.model;

import org.jetbrains.annotations.NotNull;

public class CustomerLoginModel implements Comparable<CustomerLoginModel>{
	private long customerId;
	private String username;
	private String password;
	
	public CustomerLoginModel() {
		
	}
	
	public CustomerLoginModel(long customerId, @NotNull String username,@NotNull  String password) {
		this.customerId = customerId;
		this.username = username;
		this.password = password;
	}

	public long getCustomerId() {
		return customerId;
	}


	public boolean setCustomerId(long customerId) {
		if (customerId < 0)
			return false;
		this.customerId = customerId;
		return true;
	}
	
	@NotNull
	public String getUsername() {
		return username;
	}

	public boolean setUsername(@NotNull String username) {
		if (!validateNewUsername(username))
			return false;
		this.username = username;
		return true;
	}
	
	@NotNull
	public String getPassword() {
		return password;
	}

	public boolean setPassword(@NotNull String password) {
		if (!validateNewPassword(password))
			return false;
		this.password = password;
		return true;
	}
	
	public boolean validateNewUsername(@NotNull String newUsername) {
		return !(newUsername.contains(" ") || 
				newUsername.contains("\n") ||
				newUsername.contains("\r") ||
				newUsername.contains("\t") ||
				newUsername.length() < 6);
	}
	
	public boolean validateNewPassword(@NotNull String newPassword) {
		return validateNewUsername(newPassword);
	}
	
	@Override
	public int compareTo(CustomerLoginModel m) {
		return this.customerId < m.customerId ? -1 :
			this.customerId == m.customerId ? 0 :
				1;
	}
}