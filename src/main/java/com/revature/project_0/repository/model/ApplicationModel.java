package com.revature.project_0.repository.model;

public class ApplicationModel implements Comparable<ApplicationModel>{
	private long applicationId;
	private long customerId;
	private long jointCustomerId;
	
	public static final int NO_ID = -1;
	private static Builder builder = new Builder(); 
	
	public static class Builder {
		private long applicationId;
		private long customerId;
		private long jointCustomerId;
		
		{
			reset();
		}
		
		private void reset() {
			applicationId = customerId = jointCustomerId = NO_ID;
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
		
		public ApplicationModel build() {
			return new ApplicationModel(applicationId, customerId, jointCustomerId);
		}
	}
	
	private ApplicationModel(long applicationId, long customerId, long jointCustomerId) {
		this.applicationId = applicationId;
		this.customerId = customerId;
		this.jointCustomerId = jointCustomerId;
	}
	
	public static Builder getBuilder() {
		builder.reset();
		return builder;
	}

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
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
}
