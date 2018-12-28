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
			double newAmount = account.getBalance() - byAmt;
			if (repository.updateBalanceOnDepositOrWithdrawal((int) account.getAccountId(), 
					newAmount)) {
				account.setBalance(newAmount);
				return TransactionOutcome.SUCCESS;				
			}
			return TransactionOutcome.ACCOUNT_FROZEN;
		}
	}
	
	public int makeDeposit(AccountInfoModel account, double byAmt) {
		if (!account.isAccountReadyForTransactions())
			return TransactionOutcome.ACCOUNT_FROZEN;
		
		double newAmount = account.getBalance() + byAmt;
		if (repository.updateBalanceOnDepositOrWithdrawal((int) account.getAccountId(), 
				newAmount)) {
			account.setBalance(newAmount);
			return TransactionOutcome.SUCCESS;				
		}
		return TransactionOutcome.ACCOUNT_FROZEN;
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
			double fromAmount = from.getBalance() - byAmt; 
			double toAmount = to.getBalance() + byAmt;
			if (repository.updateBalancesOnTransfer((int)from.getAccountId(), 
					fromAmount, 
					(int)to.getAccountId(), 
					toAmount)) {
				from.setBalance(fromAmount);
				to.setBalance(toAmount);
				return TransactionOutcome.SUCCESS;
			}
			return TransactionOutcome.ACCOUNT_FROZEN;
		}
	}
}