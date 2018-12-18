package com.revature.project_0.repository.model;

import com.revature.project_0.util.Util;

public class ApplicationModel implements Comparable<ApplicationModel>{
	private long applicationId;
	private long customerId;
	private long jointCustomerId;
	private int type;
	
	public static final int NO_ID = -1;
	private static Builder builder = new Builder(); 
	
	public static class Builder {
		private long applicationId;
		private long customerId;
		private long jointCustomerId;
		private int type;
		
		{
			reset();
		}
		
		private void reset() {
			applicationId = customerId = jointCustomerId = NO_ID;
			type = AccountType.CHECKING;
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
			return new ApplicationModel(applicationId, customerId, jointCustomerId, type);
		}
	}
	
	private ApplicationModel(long applicationId, long customerId, long jointCustomerId, int type) {
		this.applicationId = applicationId;
		this.customerId = customerId;
		this.jointCustomerId = jointCustomerId;
		this.type = type;
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

	public void setType(int type) {
		this.type = type;
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
				.append("\nType: ")
				.append(prettyPrintType())
				.toString();
	}
}
