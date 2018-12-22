package com.revature.project_0.entity;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;

public class FundsTransactionManager {
	private final Repository repository; 
	
	public FundsTransactionManager(Repository repository) {
		this.repository = repository;
	}

	public int makeWithdrawal(AccountInfoModel account, double byAmt) {
		if (!account.isAccountReadyForTransactions())
			return TransactionOutcome.ACCOUNT_FROZEN;
		else if (byAmt > account.getBalance())
			return TransactionOutcome.INSUFFICIENT_FUNDS;
		else {
			account.setBalance(account.getBalance() - byAmt);
			return TransactionOutcome.SUCCESS;
		}
		// TODO push changes to Repository; It currently updates the model, But the data will have to be pushed from the DAO to remote
	}
	
	public int makeDeposit(AccountInfoModel account, double byAmt) {
		if (!account.isAccountReadyForTransactions())
			return TransactionOutcome.ACCOUNT_FROZEN;
		
		account.setBalance(account.getBalance() + byAmt);
		return TransactionOutcome.SUCCESS;
		// TODO push changes to Repository; It currently updates the model, But the data will have to be pushed from the DAO to remote
	}
	
	public int makeTransferOfFunds(AccountInfoModel from,
			AccountInfoModel to,
			double byAmt) {
		if (!from.isAccountReadyForTransactions())
			return TransactionOutcome.ACCOUNT_FROZEN;
		else if (!to.isAccountReadyForTransactions())
			return TransactionOutcome.RECIPIENT_ACCOUNT_FROZEN;
		else if (byAmt > from.getBalance())
			return TransactionOutcome.INSUFFICIENT_FUNDS;
		else {
			from.setBalance(from.getBalance() - byAmt);
			to.setBalance(to.getBalance() + byAmt);
			return TransactionOutcome.SUCCESS;
		}
		// TODO push changes to Repository; It currently updates the model, But the data will have to be pushed from the DAO to remote
	}
}