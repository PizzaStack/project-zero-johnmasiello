package com.revature.project_0.repository.model;

import org.jetbrains.annotations.NotNull;

public class CustomerLoginModel implements Comparable<CustomerLoginModel>{
	private final String username;
	private final long customerId;
	private String password;
	
	private static final Builder builder = new Builder();
	public static final int NO_ID = -1;
	
	public static class Builder {
		private String username;
		private long customerId;
		private String password;
		
		{
			reset();
		}
		
		private void reset() {
			username = password = "";
			customerId = NO_ID;
		}
		
		public Builder withUsername(String username) {
			this.username = username;
			return this;
		}
		public Builder withCustomerId(long customerId) {
			this.customerId = customerId;
			return this;
		}
		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}
		
		public CustomerLoginModel build() {
			return new CustomerLoginModel(username, customerId, password);
		}
	}
	
	public static Builder getBuilder() {
		builder.reset();
		return builder;
	}
	
	private CustomerLoginModel(@NotNull String username, long customerId, @NotNull String password) {
		this.username = username;
		this.customerId = customerId;
		this.password = password;
	}

	public long getCustomerId() {
		return customerId;
	}
	
	@NotNull
	public String getUsername() {
		return username;
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
		return newUsername != null && 
				!(newUsername.contains(" ") || 
				newUsername.contains("\n") ||
				newUsername.contains("\r") ||
				newUsername.contains("\t") ||
				newUsername.length() < 6);
	}
	
	public boolean validateNewPassword(@NotNull String newPassword) {
		return newPassword != null && 
				validateNewUsername(newPassword);
	}
	
	@Override
	public int compareTo(CustomerLoginModel m) {
		return username.compareTo(m.username);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof CustomerLoginModel && compareTo((CustomerLoginModel)obj) == 0;
	}
}