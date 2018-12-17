package com.revature.project_0.repository.model;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class CustomerLoginModelTest {
	private CustomerLoginModel customerLoginModel;
	
	@Before
	public void init() {
		customerLoginModel = CustomerLoginModel.getBuilder()
				.build();
	}
	
	@Test
	public void buildWithUsername() {
		final String username = "abcdefg";
		CustomerLoginModel customerLoginModel = CustomerLoginModel.getBuilder()
				.withUsername(username)
				.build();
		assertEquals(username, customerLoginModel.getUsername());
	}

	@Test
	public void validatingTooShortUsernameFails() {
		assertFalse(customerLoginModel.validateNewUsername("asdf"));
	}
	
	@Test
	public void validating6characterUsernamePasses() {
		assertTrue(customerLoginModel.validateNewUsername("John83"));
	}
	
	@Test
	public void validatingWhiteSpaceInPasswordFails() {
		assertFalse(customerLoginModel.validateNewPassword("43 6577"));
	}
	
	@Test
	public void validating6CharacterPasswordPasses() {
		assertTrue(customerLoginModel.validateNewPassword("swordf"));
	}
	
	@Test
	public void outOfBoxModelEqualsOutOfBoxModel() {
		assertTrue(customerLoginModel.equals(CustomerLoginModel.getBuilder().build()));
	}
	
	@Test 
	public void notEqualToNull() {
		assertFalse(customerLoginModel.equals(null));
	}
}
