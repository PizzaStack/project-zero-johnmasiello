package com.revature.project_0.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.project_0.repository.model.*;
import com.revature.project_0.util.Util;

public class RepositoryTest {
	private MockRepository repository;
	private static final String EMP_ID = "EMP_ID_01";
	private static final String ADMIN_ID = "ADMIN";
	
	@Before
	public void init() {
		repository = new MockRepository();
	}
	@Test
	public void createRepository() {
		Repository repository = new MockRepository();
	}
	@Test
	public void nullUsernameIsNeitherValidNorUnique() {
		assertFalse(repository.isValidAndUniqueUsername(null));
	}
	@Test
	public void badUsernameIsNeitherValidNorUnique() {
		assertFalse(repository.isValidAndUniqueUsername(""));
	}
	@Test
	public void isValidPassword() {
		assertTrue(repository.isValidPassword("123abc"));
	}
	@Test
	public void addRecordToCustomerLoginTable() {
		assertTrue(repository.
				createNewCustomerUponValidUsernameAndPassword("_John_", "psswrd") != null);
	}
	@Test
	public void failValidateRequestedUsernameAsDuplicate() {
		repository.
			createNewCustomerUponValidUsernameAndPassword("_John_", "psswrd");
		assertFalse(repository.isValidAndUniqueUsername("_John_"));
	}
	@Test
	public void passValidateRequestedUsernameNotDuplicate() {
		repository.
			createNewCustomerUponValidUsernameAndPassword("_John_", "psswrd");
		assertTrue(repository.isValidAndUniqueUsername("_John__"));
	}
	@Test
	public void addRecordToPersonalInfoTable() {
		assertTrue(repository
				.createOrUpdateNewPersonalInformation(new PersonalInfoModel.Builder()
						.withCustomerId(0)
						.withFirstName("John")
						.withLastName("Masiello")
						.withDob(Util.getCurrentDate())
						.build()
						) != null);
	}
	@Test
	public void addRecordToApplicationTable() {
		assertTrue(repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(0)
						.withType(AccountType.SAVINGS)
						.build()) != null);
	}
	@Test
	public void newApplicationRecordIndicatesTypeSavings() {
		ApplicationModel record = repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(0)
						.withType(AccountType.SAVINGS)
						.build());
		long appId = record.getApplicationId();
		assertEquals(AccountType.SAVINGS, repository.getApplicationTable().selectRecord(appId).getType());
	}
	@Test
	public void rejectApplication() {
		ApplicationModel applicationRequest = repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(0)
						.withType(AccountType.SAVINGS)
						.build());
		assertTrue(repository.rejectApplication(applicationRequest, EMP_ID) != null);
	}
	@Test
	public void approveApplication() {
		ApplicationModel applicationRequest = repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(0)
						.withType(AccountType.SAVINGS)
						.build());
		assertTrue(repository.approveApplication(applicationRequest, EMP_ID) != null);
	}
	@Test
	public void approveApplicationGrowsAccountTable() {
		ApplicationModel applicationRequest = repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(0)
						.withType(AccountType.SAVINGS)
						.build());
		repository.approveApplication(applicationRequest, EMP_ID);
		assertTrue(1 == repository.getAccountInfoTable().generateNextPrimaryKey());
	}
	@Test
	public void getAllAccountsNotEmpty() {
		ApplicationModel applicationRequest = repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(33)
						.withType(AccountType.SAVINGS)
						.build());
		repository.approveApplication(applicationRequest, EMP_ID);
		applicationRequest = repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(38)
						.withType(AccountType.CHECKING)
						.build());
		repository.approveApplication(applicationRequest, EMP_ID);
		assertEquals(2, repository.getAllAccounts().size());
		System.out.println(Util.printAllRecords(repository.getAllAccounts()));
	}
	@Test
	public void cancelAccount() {
		ApplicationModel applicationRequest = repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(33)
						.withType(AccountType.SAVINGS)
						.build());
		AccountInfoModel account = repository.approveApplication(applicationRequest, EMP_ID);
		assertNotEquals(null, repository.cancelAccount(account.getAccountId()));
	}
	@Test
	public void cancelAccountWithPostConditionAccountMarkedAsClosed() {
		ApplicationModel applicationRequest = repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(33)
						.withType(AccountType.SAVINGS)
						.build());
		AccountInfoModel account = repository.approveApplication(applicationRequest, EMP_ID);
		account = repository.cancelAccount(account.getAccountId());
		assertEquals(AccountStatus.CLOSED, account.getStatus());
	}
	@Test
	public void approveAccount() {
		final long accountId = 0;
		repository.getAccountInfoTable().addRecord(accountId, new AccountInfoModel.Builder()
				.withAccountId(0)
				.build());
		assertEquals(ADMIN_ID, repository.approveAccount(accountId, ADMIN_ID).getAcctApproverId());
	}
	@Test
	public void denyAccount() {
		final long accountId = 0;
		repository.getAccountInfoTable().addRecord(accountId, new AccountInfoModel.Builder()
				.withAccountId(0)
				.build());
		assertEquals(AccountStatus.DENIED, 
				repository.denyAccount(accountId, ADMIN_ID).getStatus());
	}
}
