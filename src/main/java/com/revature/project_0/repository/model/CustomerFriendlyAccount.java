package com.revature.project_0.repository.model;

import org.jetbrains.annotations.NotNull;

import com.revature.project_0.util.Util;

public class CustomerFriendlyAccount {
	private final @NotNull AccountInfoModel account;

	public CustomerFriendlyAccount(@NotNull AccountInfoModel account) {
		this.account = account;
	}

	@Override
	public String toString() {
		final String DELIMITER = Util.PRINT_COLUMN_DELIMITER;
		return new StringBuilder()
		.append("Id: ")
		.append(Util.zeroPadCondensedId(account.getAccountId()))
		.append(DELIMITER)
		.append("Name: ")
		.append(account.getAccountName())
		.append(DELIMITER).append("Type: ")
		.append(account.prettyPrintType())
		.append(DELIMITER)
		.append("Opened on: ")
		.append(Util.printDate_NoTime(account.getDateOpened()))
		.append(DELIMITER)
		.append("Balance: ")
		.append(Util.currencyFormat(account.getBalance()))
		.toString();
	}
}
