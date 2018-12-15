package com.revature.project_0.repository.model;

import java.util.Date;

import org.jetbrains.annotations.Nullable;

public class AccountInfoModel {
	private long accountId;
	private long customerId;
	private long jointCustomerId;
	private Date dateOpened;
	private Date dateClosed;
	private int type;
	private int status;
	private double balance;
	
	private static final Builder builder = new Builder();
	
	public static final int NO_ID = -1;
	
	public static class AccountType {
		public static final int CHECKING = 0;
		public static final int SAVINGS = 1;
	}
	
	public static class AccountStatus {
		public static final int OPENED = 0;
		public static final int CLOSED = 1;
	}
	
	public final static class Builder {
		private long accountId;
		private long customerId;
		private long jointCustomerId;
		private Date dateOpened;
		private Date dateClosed;
		private int type = AccountType.CHECKING;
		private int status = AccountStatus.OPENED;
		private double balance;
		
		{
			reset();
		}
		
		private void reset() {
			accountId = NO_ID;
			customerId = NO_ID;
			jointCustomerId = NO_ID;
			dateOpened = null;
			dateClosed = null;
			type = AccountType.CHECKING;
			status = AccountStatus.OPENED;
			balance = 0;
		}
		
		private Builder() {}
		
		
		public Builder withAccountId(long accountId) {
			this.accountId = accountId;
			return this;
		}
		public Builder withCustomerId(Long customerId) {
			if (customerId != null)
				this.customerId = customerId;
			return this;
		}
		public Builder withJointCustomerId(@Nullable Long jointCustomerId) {
			if (jointCustomerId != null)
				this.jointCustomerId = jointCustomerId;
			return this;
		}
		public Builder withDateOpened(Date dateOpened) {
			this.dateOpened = dateOpened;
			return this;
		}
		public Builder withDateClosed(Date dateClosed) {
			this.dateClosed = dateClosed;
			return this;
		}
		public Builder withType(int type) {
			this.type = type;
			return this;
		}
		public Builder withStatus(int status) {
			this.status = status;
			return this;
		}
		public Builder withBalance(double balance) {
			this.balance = balance;
			return this;
		}
		
		public AccountInfoModel build() {
			return new AccountInfoModel(accountId, accountId, accountId, dateClosed, dateClosed, status, status, balance);
		}
	}
	
	private AccountInfoModel(long accountId, long customerId, long jointCustomerId, Date dateOpened, Date dateClosed,
			int type, int status, double balance) {
		this.accountId = accountId;
		this.customerId = customerId;
		this.jointCustomerId = jointCustomerId;
		this.dateOpened = dateOpened;
		this.dateClosed = dateClosed;
		this.type = type;
		this.status = status;
		this.balance = balance;
	}
	
	public static Builder getBuilder() {
		builder.reset();
		return builder;
	}

	public long getAccountId() {
		return accountId;
	}

	public boolean setAccountId(long accountId) {
		if (accountId < 0)
			return false;
		this.accountId = accountId;
		return true;
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

	public long getJointCustomerId() {
		return jointCustomerId;
	}

	public boolean setJointCustomerId(long jointCustomerId) {
		if (jointCustomerId < 0)
			return false;
		this.jointCustomerId = jointCustomerId;
		return true;
	}

	public Date getDateOpened() {
		return dateOpened;
	}

	public boolean setDateOpened(Date dateOpened) {
		if (dateOpened == null)
			return false;
		this.dateOpened = dateOpened;
		return true;
	}

	public Date getDateClosed() {
		return dateClosed;
	}

	public boolean setDateClosed(Date dateClosed) {
		if (dateClosed == null)
			return false;
		this.dateClosed = dateClosed;
		return true;
	}

	public int getType() {
		switch (type) {
			case AccountType.SAVINGS:
				return AccountType.SAVINGS;
		}
		return AccountType.CHECKING;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		switch (status) {
		case AccountStatus.CLOSED:
				return AccountStatus.CLOSED;
		}
		return AccountStatus.OPENED;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getBalance() {
		return balance;
	}

	public boolean setBalance(double balance) {
		if (balance < 0)
			return false;
		this.balance = balance;
		return true;
	}
}
