package com.revature.project_0.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.AccountStatus;

public class AdministratorTest {
//	@Test
	public void TestMakeDeposit() {
		final double AMOUNT = 500.00;
		Administrator administrator = new Administrator(new Repository());
		FundsTransactionManager ft = administrator.getFundsTransactionManager();
		int result = ft.makeDeposit(AccountInfoModel.getBuilder()
				.withAccountId(5)
				.withBalance(0)
				.withStatus(AccountStatus.APPROVED)
				.build(), AMOUNT);
		assertEquals(TransactionOutcome.SUCCESS, result);
	}
//	@Test
	public void TestMakeWithdrawalFail() {
		final double AMOUNT = 600.00;
		Repository repository = new Repository();
		Administrator administrator = new Administrator(repository);
		AccountInfoModel account = repository.getAccountInfo(5);
		assertNotNull(account);
		FundsTransactionManager ft = administrator.getFundsTransactionManager();
		int result = ft.makeWithdrawal(account, AMOUNT);
		assertEquals(TransactionOutcome.INSUFFICIENT_FUNDS, result);
		assertTrue(500.00 == account.getBalance());
	}
//	@Test
	public void TestMakeWithdrawalPass() {
		final double AMOUNT = 450.00;
		Repository repository = new Repository();
		Administrator administrator = new Administrator(repository);
		AccountInfoModel account = repository.getAccountInfo(5);
		assertNotNull(account);
		FundsTransactionManager ft = administrator.getFundsTransactionManager();
		int result = ft.makeWithdrawal(account, AMOUNT);
		assertEquals(TransactionOutcome.SUCCESS, result);
	}
//	@Test
	public void TestMakeTransferSuccess() {
		final double AMOUNT = 15.00;
		Repository repository = new Repository();
		Administrator administrator = new Administrator(repository);
		AccountInfoModel from = repository.getAccountInfo(5);
		assertNotNull(from);
		assertEquals(AccountStatus.APPROVED, from.getStatus());
		AccountInfoModel to = repository.getAccountInfo(2);
		assertNotNull(to);
		administrator.setEmployeeId("ApproverOfAccount");
		administrator.approveAccount(to.getAccountId());
		assertEquals(AccountStatus.APPROVED, to.getStatus());
		FundsTransactionManager ft = administrator.getFundsTransactionManager();
		int result = ft.makeTransferOfFunds(to, from, AMOUNT);
		assertEquals(TransactionOutcome.SUCCESS, result);
	}
	@After
	public void finish() {
		ConnectionHelper.getinstance().closeConnection();
	}
}
