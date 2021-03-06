package com.revature.project_0.repository.model;

import java.time.LocalDate;

import org.jetbrains.annotations.Nullable;

import com.revature.project_0.util.Util;

public class AccountInfoModel implements Comparable<AccountInfoModel> {
	private final long accountId;
	private String accountName;
	private final long customerId;
	private final long jointCustomerId;
	private LocalDate dateOpened;
	private LocalDate dateClosed;
	private int type;
	private int status;
	private double balance;
	private String approved_2;
	private String approved_1;
	
	private static final String DELIMITER = Util.PRINT_COLUMN_DELIMITER;
	private static final Builder builder = new Builder();
	
	public static final long NO_ID = -1;
	
	public final static class Builder {
		private long accountId;
		private String accountName;
		private long customerId;
		private long jointCustomerId;
		private LocalDate dateOpened;
		private LocalDate dateClosed;
		private int type;
		private int status;
		private double balance;
		private String approved2;
		private String approved1;
		
		{
			reset();
		}
		
		private void reset() {
			accountId = NO_ID;
			customerId = NO_ID;
			jointCustomerId = NO_ID;
			accountName = "";
			dateOpened = null;
			dateClosed = null;
			type = AccountType.CHECKING;
			status = AccountStatus.OPENED;
			balance = 0;
			approved2 = approved1 = null;
		}		
		
		public Builder withAccountId(long accountId) {
			this.accountId = accountId;
			return this;
		}
		public Builder withAccountName(String accountName) {
			this.accountName = accountName;
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
		public Builder withDateOpened(LocalDate dateOpened) {
			this.dateOpened = dateOpened;
			return this;
		}
		public Builder withDateClosed(LocalDate dateClosed) {
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
		public Builder withAcctApproverId(String approverId) {
			this.approved2 = approverId;
			return this;
		}
		public Builder withAppAproverId(String approverId) {
			this.approved1 = approverId;
			return this;
		}
		
		public AccountInfoModel build() {
			return new AccountInfoModel(accountId, accountName, customerId, jointCustomerId, dateOpened, dateClosed, 
					type, status, balance, approved2, approved1);
		}
	}
	
	private AccountInfoModel(long accountId, String accountName, long customerId, long jointCustomerId, LocalDate dateOpened, LocalDate dateClosed,
			int type, int status, double balance, String approver2Id, String approver1Id) {
		this.accountId = accountId;
		this.accountName = accountName;
		this.customerId = customerId;
		this.jointCustomerId = jointCustomerId;
		this.dateOpened = dateOpened;
		this.dateClosed = dateClosed;
		this.type = type;
		this.status = status;
		this.balance = balance;
		this.approved_2 = approver2Id;
		this.approved_1 = approver1Id;
	}
	
	public static Builder getBuilder() {
		builder.reset();
		return builder;
	}

	public long getAccountId() {
		return accountId;
	}
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public long getCustomerId() {
		return customerId;
	}

	public long getJointCustomerId() {
		return jointCustomerId;
	}

	public LocalDate getDateOpened() {
		return dateOpened;
	}

	public boolean setDateOpened(LocalDate dateOpened) {
		if (dateOpened == null)
			return false;
		this.dateOpened = dateOpened;
		return true;
	}

	public LocalDate getDateClosed() {
		return dateClosed;
	}

	public boolean setDateClosed(LocalDate dateClosed) {
		if (dateClosed == null)
			return false;
		this.dateClosed = dateClosed;
		return true;
	}

	public int getType() {
		return type;
	}
	
	public boolean isAccountReadyForTransactions() {
		return status == AccountStatus.APPROVED;
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
		return status;
	}
	
	public String prettyPrintStatus() {
		switch (getStatus()) {
		case AccountStatus.CLOSED:
			return "CLOSED";
		case AccountStatus.APPROVED:
			return "APPROVED";
		case AccountStatus.DENIED:
			return "DENIED";
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
	
	public String getAcctApproverId() {
		return approved_2;
	}

	public void setAcctApproverId(String approverId) {
		this.approved_2 = approverId;
	}

	public String getAppApproverId() {
		return approved_1;
	}

	public void setAppApproverId(String approverId) {
		this.approved_1 = approverId;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("Account Id: ")
				.append(Util.zeroPadId(accountId))
				.append(DELIMITER).append("Account Name: ")
				.append(accountName)
				.append(DELIMITER).append("Balance: ")
				.append(Util.currencyFormat(balance))
				.append(DELIMITER).append("Customer Id: ")
				.append(Util.zeroPadCondensedId(customerId))
				.append(DELIMITER).append("Customer Joint Id: ")
				.append(jointCustomerId > NO_ID ? Util.zeroPadCondensedId(jointCustomerId) : Util.NOT_AVAILABLE)
				.append(DELIMITER).append("Opened On: ")
				.append(dateOpened != null ? Util.printDate_NoTime(dateOpened) : Util.NOT_AVAILABLE)
				.append(DELIMITER).append("Closed On: ")
				.append(dateClosed != null ? Util.printDate_NoTime(dateClosed) : Util.NOT_AVAILABLE)
				.append(DELIMITER).append("Type: ")
				.append(prettyPrintType())
				.append(DELIMITER).append("Status: ")
				.append(prettyPrintStatus())
				.append(DELIMITER).append("App Approved 1: ")
				.append(approved_1 != null ? approved_1 : Util.NOT_AVAILABLE)
				.append(DELIMITER).append("Acct Approved 2: ")
				.append(approved_2 != null ? approved_2 : Util.NOT_AVAILABLE)
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
