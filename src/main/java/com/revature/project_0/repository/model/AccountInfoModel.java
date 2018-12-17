package com.revature.project_0.repository.model;

import java.util.Date;

import org.jetbrains.annotations.Nullable;

import com.revature.project_0.util.Util;

public class AccountInfoModel implements Comparable<AccountInfoModel> {
	private final long accountId;
	private final long customerId;
	private final long jointCustomerId;
	private Date dateOpened;
	private Date dateClosed;
	private int type;
	private int status;
	private double balance;
	
	private static final Builder builder = new Builder();
	
	public static final long NO_ID = -1;
	
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
			return new AccountInfoModel(accountId, customerId, jointCustomerId, dateOpened, dateClosed, type, status, balance);
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

	public long getCustomerId() {
		return customerId;
	}

	public long getJointCustomerId() {
		return jointCustomerId;
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
			default:
				return AccountType.CHECKING;				
		}
	}
	
	public String prettyPrintType() {
		switch (getType()) {
		case AccountType.SAVINGS:
			return "SAVINGS";
		default:
			return "CHECKING";
		}
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		switch (status) {
		case AccountStatus.CLOSED:
				return AccountStatus.CLOSED;
		default:
			return AccountStatus.OPENED;
		}
	}
	
	public String prettyPrintStatus() {
		switch (getStatus()) {
		case AccountStatus.CLOSED:
			return "CLOSED";
		default:
			return "OPENED";
		}
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
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Account Id: ")
				.append(Util.zeroPadId(accountId))
				.append("\nBalance: ")
				.append(Util.currencyFormat(balance))
				.append("\nCustomer \tId: ")
				.append(Util.zeroPadCondensedId(customerId))
				.append("\nCustomer, Joint Id: ")
				.append(jointCustomerId > NO_ID ? Util.zeroPadCondensedId(jointCustomerId) : "")
				.append("\nOpened On: ")
				.append(dateOpened != null ? dateOpened : Util.NOT_AVAILABLE)
				.append("\nClosed On: ")
				.append(dateClosed != null ? dateClosed : Util.NOT_AVAILABLE)
				.append("\nType: ")
				.append(prettyPrintType())
				.append("\nStatus: ")
				.append(prettyPrintStatus())
				.toString();
	}

	@Override
	public int compareTo(AccountInfoModel m) {
		return this.accountId < m.accountId ? -1 :
			this.accountId > m.accountId ? 1 :
				0;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AccountInfoModel && compareTo((AccountInfoModel)obj) == 0;
	}
}
