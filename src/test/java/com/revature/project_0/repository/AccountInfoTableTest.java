package com.revature.project_0.repository;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.util.Util;

public class AccountInfoTableTest {
	private AccountInfoModel[] accountInfos;
	private AccountInfoTable accountInfoTable;
	
	@Before
	public void init() {
		accountInfos = new AccountInfoModel[] {
				AccountInfoModel.getBuilder()
					.withAccountId(0)
					.withCustomerId(11l)
					.build(),
				AccountInfoModel.getBuilder()
					.withAccountId(1)
					.withCustomerId(12l)
					.build(),
				AccountInfoModel.getBuilder()
					.withAccountId(3)
					.withCustomerId(13l)
					.withJointCustomerId(11l)
					.build()
		};
		
		accountInfoTable = new AccountInfoTable();
	}
	
	@Test
	public void buildAccountInfoTable() {
		AccountInfoTable accountInfoTable = new AccountInfoTable();
	}
	
	@Test
	public void addAccountRecords() {
		for (AccountInfoModel a : accountInfos)
			assertTrue(accountInfoTable.addRecord(a.getAccountId(), a));
	}
	
	@Test
	public void selectAccountIdEqualTo3() {
		for (AccountInfoModel a : accountInfos)
			accountInfoTable.addRecord(a.getAccountId(), a);
		assertEquals(13l, accountInfoTable.selectRecord(3l).getCustomerId());
	}
	
	@Test public void deleteARecord() {
		accountInfoTable.addRecord(0l, accountInfos[0]);
		assertTrue(accountInfoTable.deleteRecord(0l));
	}
	
	@Test
	public void delete3RecordsMakeTableEmpty() {
		for (AccountInfoModel a : accountInfos)
			accountInfoTable.addRecord(a.getAccountId(), a);
		accountInfoTable.deleteRecord(0l);
		accountInfoTable.deleteRecord(1l);
		accountInfoTable.deleteRecord(3l);
		assertTrue(accountInfoTable.getTable().isEmpty());
	}
	
	@Test
	public void getAllAssociatedAccountsForCustomer() {
		for (AccountInfoModel a : accountInfos)
			accountInfoTable.addRecord(a.getAccountId(), a);
		
		final long customerId = 11l;
		List<AccountInfoModel> associatedAccounts 
			= accountInfoTable.getAllAssociatedAccounts(customerId);

		assertEquals(0, associatedAccounts.get(0).getAccountId());
		assertEquals(3, associatedAccounts.get(1).getAccountId());
		
		System.out.println(Util.printAllRecords(associatedAccounts));
	}
	
	@Test
	public void testFirstPrimaryKey( ) {
		assertThat(0l, comparesEqualTo(accountInfoTable.generateNextPrimaryKey()));
	}
	
	@Test
	public void testNextPrimaryKey( ) {
		accountInfoTable.addRecord(0, accountInfos[0]);
		assertThat(1l, comparesEqualTo(accountInfoTable.generateNextPrimaryKey()));
	}
}