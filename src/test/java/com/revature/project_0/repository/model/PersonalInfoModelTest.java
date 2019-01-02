package com.revature.project_0.repository.model;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

public class PersonalInfoModelTest {
	private PersonalInfoModel personalInfoModel;
	
	@Before
	public void init() {
		personalInfoModel = PersonalInfoModel.getBuilder().build();
	}
	
	@Test
	public void createPersonalInfoModelWithBuilder() {
		PersonalInfoModel personalInfoModel = PersonalInfoModel.getBuilder().build();
	}
	
	@Test
	public void buildPersonalInfoModelWithEmail() {
		final String email = "john.masiello@yahoo.com";
		PersonalInfoModel personalInfoModel = PersonalInfoModel.getBuilder()
				.withEmail(email)
				.build();
		assertEquals(email, personalInfoModel.getEmail());
	}
	
	@Test 
	public void buildTwoDisimilarAccountsInfoModelsWithSingletonBuilder() {
		PersonalInfoModel m1 = PersonalInfoModel.getBuilder()
				.withFirstName("Bob")
				.build();
		PersonalInfoModel m2 = PersonalInfoModel.getBuilder()
				.build();
		assertEquals("", m2.getFirstName());
	}
	
	@Test
	public void validateBadSsnNotSet() {
		assertFalse(personalInfoModel.setSSN("abc123"));
	}
	
	@Test
	public void validateSSnSet() {
		final String ssn = "123-45-6789";
		personalInfoModel.setSSN(ssn);
		assertEquals("123456789", personalInfoModel.getSSN());
	}
	
	@Test
	public void validateIncompletPhoneNumberNotSet() {
		assertFalse(personalInfoModel.setPhoneNumber("444-7777"));
	}
	
	@Test
	public void validatePhoneNumber() {
		final String phn = "(810)444-7777";
		personalInfoModel.setPhoneNumber(phn);
		assertEquals("8104447777", personalInfoModel.getPhoneNumber());
	}
	
	@Test
	public void validatePrettyPrintPhoneNumber() {
		final String phn = "(810)444-7777";
		personalInfoModel.setPhoneNumber(phn);
		assertEquals(phn, personalInfoModel.prettyPrintPhoneNumber());
	}
	
	@Test
	public void sameCustomerIdComparesAsEqual() {
		PersonalInfoModel p1, p2;
		
		p1 = PersonalInfoModel.getBuilder().build();
		p2 = PersonalInfoModel.getBuilder().build();
		
		assertThat(0, comparesEqualTo(p1.compareTo(p2)));
	}
	
	@Test
	public void lesserCustomerIdComparesAsLesser() {
		PersonalInfoModel p1, p2;
		
		p1 = PersonalInfoModel.getBuilder()
				.withCustomerId(0)
				.build();
		p2 = PersonalInfoModel.getBuilder()
				.withCustomerId(1)
				.build();
		
		assertThat(0, greaterThan(p1.compareTo(p2)));
	}
	
	@Test
	public void greaterCustomerIdComparesAsGreater() {
		PersonalInfoModel p1, p2;
		
		p1 = PersonalInfoModel.getBuilder()
				.withCustomerId(1)
				.build();
		p2 = PersonalInfoModel.getBuilder()
				.withCustomerId(0)
				.build();
		
		assertThat(0, lessThan(p1.compareTo(p2)));
	}
	
	@Test
	public void outOfBoxModelEqualsOutOfBoxModel() {
		assertTrue(personalInfoModel.equals(PersonalInfoModel.getBuilder().build()));
	}
	
	@Test 
	public void notEqualToNull() {
		assertFalse(personalInfoModel.equals(null));
	}
}
