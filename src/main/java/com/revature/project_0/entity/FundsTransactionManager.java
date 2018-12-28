package com.revature.project_0.entity;

import com.revature.project_0.io.Log;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;	

public class FundsTransactionManager {
	private final Repository repository; 
	
	private final static String DEPOSIT_PRINT_FORMAT = "%s %-10s amt $%012.2f end balance $%012.2f #%08d";
	private final static String TRANSFER_PRINT_FORMAT = 
			"%s %-10s amt $%012.2f end balance $%012.2f to #%08d end balance $%012.2f from #%08d";
	
	public FundsTransactionManager(Repository repository) {
		this.repository = repository;
	}

	public int makeWithdrawal(AccountInfoModel account, double byAmt, String who) {
		if (!account.isAccountReadyForTransactions())
			return TransactionOutcome.ACCOUNT_FROZEN;
		else if (byAmt > account.getBalance())
			return TransactionOutcome.INSUFFICIENT_FUNDS;
		else {
			double newAmount = account.getBalance() - byAmt;
			if (repository.updateBalanceOnDepositOrWithdrawal((int) account.getAccountId(), 
					newAmount)) {
				account.setBalance(newAmount);
				Log.transactionsLogger.info(String.format(DEPOSIT_PRINT_FORMAT, 
						"W",
						who,
						byAmt,
						account.getBalance(),
						account.getAccountId()));
				return TransactionOutcome.SUCCESS;				
			}
			return TransactionOutcome.ACCOUNT_FROZEN;
		}
	}
	
	public int makeDeposit(AccountInfoModel account, double byAmt, String who) {
		if (!account.isAccountReadyForTransactions())
			return TransactionOutcome.ACCOUNT_FROZEN;
		
		double newAmount = account.getBalance() + byAmt;
		if (repository.updateBalanceOnDepositOrWithdrawal((int) account.getAccountId(), 
				newAmount)) {
			account.setBalance(newAmount);
			Log.transactionsLogger.info(String.format(DEPOSIT_PRINT_FORMAT, 
					"D",
					who,
					byAmt,
					account.getBalance(),
					account.getAccountId()));
			return TransactionOutcome.SUCCESS;				
		}
		return TransactionOutcome.ACCOUNT_FROZEN;
	}
	
	public int makeTransferOfFunds(AccountInfoModel from,
			AccountInfoModel to,
			double byAmt,
			String who) {
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
				Log.transactionsLogger.info(String.format(TRANSFER_PRINT_FORMAT,
						"T",
						who,
						byAmt,
						to.getBalance(),
						to.getAccountId(),
						from.getBalance(),
						from.getAccountId()));					
				return TransactionOutcome.SUCCESS;
			}
			return TransactionOutcome.ACCOUNT_FROZEN;
		}
	}
}