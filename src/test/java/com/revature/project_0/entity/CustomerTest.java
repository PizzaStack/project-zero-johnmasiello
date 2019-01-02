package com.revature.project_0.entity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.revature.project_0.repository.MockRepository;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.CustomerLoginModel;
import com.revature.project_0.repository.model.PersonalInfoModel;

public class CustomerTest {
	@Test
	public void testResetPersonalInfo() {
		new Customer(new MockRepository()).resetPersonalInfo();
	}
	@Test
	public void testFundsTransactionManager() {
		assertNotNull(new Customer(new MockRepository()).getFundsTransactionManager());
	}
	@Test
	public void customerBehaviorSuccess() {
		Repository repository = new MockRepository();
		Customer customer = new Customer(repository);
		assertTrue(customer.isValidUsername("customer"));
		assertTrue(customer.isUniqueUsername("customer"));
		assertTrue(customer.isValidPassword("password"));
		assertTrue(customer.createNewCustomer("customer", "password"));
		assertEquals("customer", customer.getUserName());
		assertEquals(0l, customer.getCustomerId());
		assertTrue(customer.signInSuccessful("customer", "password"));
		assertTrue(customer.isSignedIn());
		assertFalse(customer.fetchAccounts(0l));
		assertFalse(customer.hasPersonalRecordOnFile());
		assertNotNull(customer.createOrUpdatePersonalInfo(PersonalInfoModel.getBuilder()
				.withCustomerId(0l)
				.build()));
		customer.resetPersonalInfo();
		assertTrue(customer.hasPersonalRecordOnFile());
		assertTrue(customer.createApplication(ApplicationModel.getBuilder()
				.withCustomerId(0l)
				.build()));
		ApplicationModel application1 = customer.getApplication();
		assertNotNull(application1);
		customer.setApplicationModel(application1);
		ApplicationModel application2 = ApplicationModel.getBuilder()
				.withCustomerId(0l)
				.withAccountName("Another account")
				.build();
		assertTrue(customer.createApplication(application2));
		assertNull(customer.pickAccountByAccountId(0l));
		assertNotNull(repository.approveApplication(application1, "TESTER"));
		assertNotNull(repository.approveApplication(application2, "TESTER"));
		assertTrue(customer.fetchAccounts(0l));
		assertNotNull(customer.pickAccountByAccountId(0l));
		assertNotNull(customer.viewAllAccounts());
		assertNotNull(customer.viewAllAcountsAsEnumerated());
		assertNotNull(customer.pickAccountByAccountId(1l));
	}
	@Test
	public void customerBehaviorFailOrNull() {
		Repository repository = new MockRepository();
		Customer customer = new Customer(repository);
		assertTrue(new Customer(repository).createNewCustomer("customer", "password"));
		assertFalse(customer.createNewCustomer("customer", "6565fgf"));
		assertNull(customer.getUserName());
		assertEquals(CustomerLoginModel.NO_ID, customer.getCustomerId());
		assertFalse(customer.signInSuccessful("customer", "6565fgf"));
		assertFalse(customer.isSignedIn());
		customer.signOut();
		customer.setPersonalInfoModel(null);
		assertNull(customer.getPersonalInfoModel());
		assertFalse(customer.hasPersonalRecordOnFile());
		customer.setPersonalInfoModel(PersonalInfoModel.getBuilder().build());
		assertTrue(customer.hasPersonalRecordOnFile());
	}
}
	