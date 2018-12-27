package com.revature.project_0.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.dao.ApplicationDao;
import com.revature.project_0.repository.dao.PersonalInfoDao;
import com.revature.project_0.repository.model.AccountType;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.PersonalInfoModel;
import com.revature.project_0.util.Util;

public class CustomerTest {
//	@Test
	public void testCreatePersonalInformation() {
		Customer customer = new Customer(new Repository());
		assertTrue(customer.signInSuccessful("John++", "SWORDFISH"));
		PersonalInfoModel personalInfo = PersonalInfoModel.getBuilder()
				.withCustomerId(customer.getCustomerId())
				.withFirstName("John")
				.withLastName("Masiello")
				.withMiddleInitial('Q')
				.withDob(Util.getCurrentDate())
				.withEmail("j@lycos.com")
				.withPhoneNumber("1231231234")
				.withSSN("987654321")
				.withBeneficiary("Salvation Army")
				.build();
		assertNotEquals(null, customer.createOrUpdatePersonalInfo(personalInfo));
	}
//	@Test
	public void testQueryPersonalInformation() {
		PersonalInfoModel model; 
		Customer customer = new Customer(new Repository());
		assertTrue(customer.signInSuccessful("John++", "SWORDFISH"));
		PersonalInfoDao dao = new PersonalInfoDao();
		model =  dao.queryPersonalInfoByCustomerId((int) customer.getCustomerId());
		assertNotNull(model);
		assertEquals("Salvation Army", model.getBeneficiary());
		model = dao.queryPersonalInfoBySSN("987654321");
		assertNotNull(model);
		assertEquals("1231231234", model.getPhoneNumber());
		assertTrue(customer.hasPersonalRecordOnFile());
	}
//	@Test
	public void testQueryAllPersonalInformation() {
		new PersonalInfoDao()
				.queryPersonalInfoForAllCustomers()
					.forEach(($) -> {System.out.println($);});
	}
//	@Test
	public void testCreateApplication() {
		Customer customer = new Customer(new Repository());
		assertTrue(customer.signInSuccessful("John++", "SWORDFISH"));
		ApplicationModel applicationModel = ApplicationModel.getBuilder()
				.withAccountName("Pouring Day")
				.withCustomerId(customer.getCustomerId())
				.withType(AccountType.SAVINGS)
				.build();
		assertTrue(customer.createApplication(applicationModel));
	}
//	@Test
	public void testQueryApplication() {
		Repository repository = new Repository();
		Customer customer = new Customer(repository);
		if (customer.signInSuccessful("John++", "SWORDFISH")) {
			long customerId = customer.getCustomerId();
			repository.getAllAssociatedApplications(customerId)
			.forEach(($)->{System.out.println($);});
			System.out.println("---------------------------------------------"
					+ "--------------------------------------------------");
			repository.getAllApplications()
			.forEach(($)->{System.out.println($);});
		}
	}
	
	@After
	public void finish() {
		ConnectionHelper.getinstance().closeConnection();
	}
}
