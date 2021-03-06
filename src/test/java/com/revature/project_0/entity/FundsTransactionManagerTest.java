package com.revature.project_0.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.revature.project_0.repository.BasicMockRepository;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.AccountStatus;

public class FundsTransactionManagerTest {
	private FundsTransactionManager fundsTransactionManager;
	@Before
	public void init() {
		fundsTransactionManager = new FundsTransactionManager(new BasicMockRepository());
	}
	@Test
	public void makeWithdrawalOnReadyAccountWithEnoughFunds() {
		AccountInfoModel account = AccountInfoModel.getBuilder()
				.withStatus(AccountStatus.APPROVED)
				.withBalance(500.00)
				.build();
		assertEquals(TransactionOutcome.SUCCESS, fundsTransactionManager
				.makeWithdrawal(account, 270, "tester"));
	}
	@Test
	public void makeWithdrawalOnReadyAccountWithInsufficientFunds() {
		AccountInfoModel account = AccountInfoModel.getBuilder()
				.withStatus(AccountStatus.APPROVED)
				.withBalance(500.00)
				.build();
		assertEquals(TransactionOutcome.INSUFFICIENT_FUNDS, fundsTransactionManager
				.makeWithdrawal(account, 750, "tester"));
	}
	@Test
	public void makeWithdrawalFromFrozenAccount() {
		AccountInfoModel account = AccountInfoModel.getBuilder()
				.withStatus(AccountStatus.DENIED)
				.withBalance(500.00)
				.build();
		assertEquals(TransactionOutcome.ACCOUNT_FROZEN, fundsTransactionManager
				.makeWithdrawal(account, 100, "tester"));
	}
	@Test
	public void testMakeDeposits() {
		AccountInfoModel account = AccountInfoModel.getBuilder()
				.withStatus(AccountStatus.DENIED)
				.withBalance(500.00)
				.build();
		assertEquals(TransactionOutcome.ACCOUNT_FROZEN, fundsTransactionManager
				.makeDeposit(account, 100, "tester"));
		account.setStatus(AccountStatus.APPROVED);
		assertEquals(TransactionOutcome.SUCCESS, fundsTransactionManager
				.makeDeposit(account, 1000000, "tester"));
	}
	@Test
	public void testMakeTransfers() {
		AccountInfoModel from = AccountInfoModel.getBuilder().build();
		AccountInfoModel to = AccountInfoModel.getBuilder().build();
		final double amount = 1;
		from.setStatus(AccountStatus.APPROVED);
		to.setStatus(AccountStatus.APPROVED);
		from.setBalance(amount);
		assertEquals(TransactionOutcome.SUCCESS, fundsTransactionManager
				.makeTransferOfFunds(from, to, amount, "tester"));
		assertThat(0d, Matchers.comparesEqualTo(from.getBalance()));
		assertThat(1d, Matchers.comparesEqualTo(to.getBalance()));
		assertEquals(TransactionOutcome.INSUFFICIENT_FUNDS, fundsTransactionManager
				.makeTransferOfFunds(from, to, amount, "tester"));
		to.setStatus(AccountStatus.CLOSED);
		assertEquals(TransactionOutcome.RECIPIENT_ACCOUNT_FROZEN, fundsTransactionManager
				.makeTransferOfFunds(from, to, amount, "tester"));
		from.setStatus(AccountStatus.CLOSED);
		assertEquals(TransactionOutcome.ACCOUNT_FROZEN, fundsTransactionManager
				.makeTransferOfFunds(from, to, amount, "tester"));
	}
}
