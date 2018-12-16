package com.revature.project_0.repository.model;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class CustomerLoginModelTest {
	private CustomerLoginModel customerLoginModel;
	
	@Before
	public void init() {
		customerLoginModel = new CustomerLoginModel(0, "", "");
	}
	@Test
	public void createCustomerLoginModel() {
		CustomerLoginModel customerLoginModel = new CustomerLoginModel(0, "john83", "secret");
	}
	
	@Test
	public void settingNegativeCustomerIdFails() {
		assertFalse(customerLoginModel.setCustomerId(-1));
	}
	
	@Test
	public void settingtooShortUsernameFails() {
		assertFalse(customerLoginModel.setUsername("asdf"));
	}
	
	@Test
	public void setting6characterUsernamePasses() {
		assertTrue(customerLoginModel.setUsername("John83"));
	}
	
	@Test
	public void settingWhiteSpaceInPasswordFails() {
		assertFalse(customerLoginModel.setPassword("43 6577"));
	}
	
	@Test
	public void setting6CharacterPasswordPasses() {
		assertTrue(customerLoginModel.setPassword("swordf"));
	}
}
