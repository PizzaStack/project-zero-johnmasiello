package com.revature.project_0.repository.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AccountInfoModelTest {
	private AccountInfoModel accountInfoModel;
	
	@Before
	public void makeDefaultModel() {
		accountInfoModel = AccountInfoModel.getBuilder().build();
	}
	
	@Test
	public void getAccountInfoModelBuilder() {
		AccountInfoModel.Builder builder = AccountInfoModel.getBuilder();
	}
	
	@Test 
	public void buildAccountInfoModel() {
		AccountInfoModel model = AccountInfoModel.getBuilder().build();
	}
	
	@Test 
	public void buildAccountInfoModelWithPositiveBalance() {
		AccountInfoModel model = AccountInfoModel.getBuilder()
				.withBalance(5.00)
				.build();
		assertTrue(5.00 == model.getBalance());
	}
	
	@Test
	public void buildAccountInfoWithSettingStatus() {
		AccountInfoModel model = AccountInfoModel.getBuilder()
				.withStatus(AccountStatus.CLOSED)
				.build();
		assertEquals(AccountStatus.CLOSED, model.getStatus());
	}
	
	@Test 
	public void buildTwoDisimilarAccountsInfoModelsWithSingletonBuilder() {
		AccountInfoModel m1 = AccountInfoModel.getBuilder()
				.withBalance(5.00)
				.build();
		AccountInfoModel m2 = AccountInfoModel.getBuilder()
				.build();
		assertTrue(0 == m2.getBalance());
	}
	
	@Test
	public void settingNullAccountOpenedDateFails() {
		assertFalse(accountInfoModel.setDateOpened(null));
	}
	
	@Test
	public void settingNullAccountClosedDateFails() {
		assertFalse(accountInfoModel.setDateClosed(null));
	}
	
	@Test
	public void settingNegativeBalanceFails() {
		assertFalse(accountInfoModel.setBalance(-123.4567));
	}
	
	@Test
	public void setGetChecking() {
		accountInfoModel.setType(AccountType.CHECKING);
		assertEquals(AccountType.CHECKING, accountInfoModel.getType());
	}
	
	@Test
	public void setGetSavings() {
		accountInfoModel.setType(AccountType.SAVINGS);
		assertEquals(AccountType.SAVINGS, accountInfoModel.getType());
	}
	
	@Test
	public void setGetOpened() {
		accountInfoModel.setType(AccountStatus.OPENED);
		assertEquals(AccountStatus.OPENED, accountInfoModel.getType());
	}
	
	@Test
	public void setGetClosed() {
		accountInfoModel.setType(AccountStatus.CLOSED);
		assertEquals(AccountStatus.CLOSED, accountInfoModel.getType());
	}
	
	@Test
	public void lesserAccountIdComparesAsLesser() {
		AccountInfoModel p1, p2;
		
		p1 = AccountInfoModel.getBuilder()
				.withAccountId(0)
				.build();
		p2 = AccountInfoModel.getBuilder()
				.withAccountId(1)
				.build();
		
		assertTrue(0 > p1.compareTo(p2));
	}
	
	@Test
	public void lesserCustomerIdComparesAsLesser() {
		AccountInfoModel p1, p2;
		
		p1 = AccountInfoModel.getBuilder()
				.withCustomerId(0l)
				.build();
		p2 = AccountInfoModel.getBuilder()
				.withCustomerId(1l)
				.build();
		
		assertTrue(0 > p1.compareTo(p2));
	}
	
	@Test
	public void lesserJointCustomerIdComparesAsLesser() {
		AccountInfoModel p1, p2;
		
		p1 = AccountInfoModel.getBuilder()
				.withJointCustomerId(0l)
				.build();
		p2 = AccountInfoModel.getBuilder()
				.withJointCustomerId(1l)
				.build();
		
		assertTrue(0 > p1.compareTo(p2));
	}
	
	@Test
	public void equalAccountIdCustomerIdJointCustomerIdComparesAsEqual() {
		AccountInfoModel p1, p2;
		
		p1 = AccountInfoModel.getBuilder()
				.withType(0)
				.build();
		p2 = AccountInfoModel.getBuilder()
				.withType(1)
				.build();
		
		assertTrue(0 == p1.compareTo(p2));
	}
	
	@Test
	public void outOfBoxModelEqualsOutOfBoxModel() {
		assertTrue(accountInfoModel.equals(AccountInfoModel.getBuilder().build()));
	}
	
	@Test 
	public void notEqualToNull() {
		assertFalse(accountInfoModel.equals(null));
	}
}
