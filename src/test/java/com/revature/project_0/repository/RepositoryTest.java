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
		assertNotNull(repository.
				createNewCustomerUponValidUsernameAndPassword("_John_", "psswrd"));
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
		assertNotNull(repository
				.createOrUpdateNewPersonalInformation(new PersonalInfoModel.Builder()
						.withCustomerId(0)
						.withFirstName("John")
						.withLastName("Masiello")
						.withDob(Util.getCurrentDate())
						.build()
						));
	}
	@Test
	public void addRecordToApplicationTable() {
		assertNotNull(repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(0)
						.withType(AccountType.SAVINGS)
						.build()));
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
		assertNotNull(repository.rejectApplication(applicationRequest, EMP_ID));
	}
	@Test
	public void approveApplication() {
		ApplicationModel applicationRequest = repository
				.createNewApplication(new ApplicationModel.Builder()
						.withCustomerId(0)
						.withType(AccountType.SAVINGS)
						.build());
		assertNotNull(repository.approveApplication(applicationRequest, EMP_ID));
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
	@Test 
	public void getTables() {
		assertNotNull(repository.getCustomerLoginTable());
		assertNotNull(repository.getPersonalInfoTable());
	}
	@Test
	public void getCustomerInfo() {
		assertTrue(repository.getAllAssociatedAccounts(1l).isEmpty());
		assertTrue(repository.getAllAssociatedApplications(1l).isEmpty());
		assertTrue(repository.getAllApplications().isEmpty());
		assertTrue(repository.getAllCustomersPersonalInformation().isEmpty());
	}
	@Test
	public void getPersonalRecord() {
		assertNull(repository.getPersonalRecord(1l));
	}
	@Test
	public void testAuthenticateCustomer() {
		assertNull(repository.authenticateCustomer("customer", null));
		CustomerLoginModel subject = repository.createNewCustomerUponValidUsernameAndPassword("customer", "password");
		assertNotNull(subject);
		assertNotNull(repository.authenticateCustomer("customer", "password"));
		assertNull(repository.authenticateCustomer("customer", "pass"));
	}
}
