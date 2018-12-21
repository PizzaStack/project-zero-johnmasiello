package com.revature.project_0.repository.model;

import org.jetbrains.annotations.NotNull;

import com.revature.project_0.util.Util;

public class ApplicationModel implements Comparable<ApplicationModel>{
	private long applicationId;
	private long customerId;
	private long jointCustomerId;
	private String jointCustomerSSN;
	private int type;
	
	public static final int NO_ID = -1;
	private static Builder builder = new Builder(); 
	
	public static class Builder {
		private long applicationId;
		private long customerId;
		private long jointCustomerId;
		private int type;
		private String jointCustomerSSN;
		
		{
			reset();
		}
		
		private void reset() {
			applicationId = customerId = jointCustomerId = NO_ID;
			type = AccountType.CHECKING;
			jointCustomerSSN = "";
		}

		public Builder withApplicationId(long applicationId) {
			this.applicationId = applicationId;
			return this;
		}

		public Builder withCustomerId(long customerId) {
			this.customerId = customerId;
			return this;
		}

		public Builder withJointCustomerId(long jointCustomerId) {
			this.jointCustomerId = jointCustomerId;
			return this;
		}
		
		public Builder withType(int type) {
			this.type = type;
			return this;
		}
		
		public ApplicationModel build() {
			return new ApplicationModel(applicationId, customerId, jointCustomerId, type,
					jointCustomerSSN);
		}
	}
	
	private ApplicationModel(long applicationId, long customerId, long jointCustomerId, int type,
			String jointCustomerSSN) {
		this.applicationId = applicationId;
		this.customerId = customerId;
		this.jointCustomerId = jointCustomerId;
		this.type = type;
		this.jointCustomerSSN = jointCustomerSSN;
	}
	
	public static Builder getBuilder() {
		builder.reset();
		return builder;
	}
	
	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	public long getApplicationId() {
		return applicationId;
	}
	
	public String prettyPrintApplicationId() {
		return Util.zeroPadCondensedId(applicationId);
	}
	
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getJointCustomerId() {
		return jointCustomerId;
	}

	public void setJointCustomerId(long jointCustomerId) {
		this.jointCustomerId = jointCustomerId;
	}
	
	public String getJointCustomerSSN() {
		return jointCustomerSSN;
	}
	
	public boolean setJointCustomerSSN(@NotNull String ssn) {
		if (ssn == null)
			return false;
		String trimmedLastSsn = trimToJustDigits(ssn);
		if (trimmedLastSsn.length() != 9)
			return false;
		this.jointCustomerSSN = trimmedLastSsn;
		return true;
	}
	private String trimToJustDigits(String raw) {
		return raw.replaceAll("[^0-9]+", "");
	}
	
	public String prettyPrintLast4SSN() {
		if (jointCustomerSSN == null ||
				jointCustomerSSN.length() == 0)
			return Util.NOT_AVAILABLE;
		return "XXX-XX-" + jointCustomerSSN.substring(5);
	}

	public int getType() {
		return type;
	}
	
	public String prettyPrintType() {
		switch (type) {
		case AccountType.SAVINGS:
			return "SAVINGS";
		default:
			return "CHECKING";
		}
	}

	public boolean setType(int type) {
		switch (type) {
		case AccountType.CHECKING:
		case AccountType.SAVINGS:
			this.type = type;		
			return true;
		} return false;
	}

	@Override
	public int compareTo(ApplicationModel m) {
		return applicationId < m.applicationId ? -1 :
			applicationId > m.applicationId ? 1 :
				0;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ApplicationModel && compareTo((ApplicationModel)obj) == 0;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Account Id: ")
				.append(Util.zeroPadId(applicationId))
				.append("\nCustomer \tId: ")
				.append(Util.zeroPadCondensedId(customerId))
				.append("\nCustomer, Joint Id: ")
				.append(jointCustomerId > NO_ID ? Util.zeroPadCondensedId(jointCustomerId) : "")
				.append("\nSSN: ")
				.append(prettyPrintLast4SSN())
				.append("\nType: ")
				.append(prettyPrintType())
				.toString();
	}
}
