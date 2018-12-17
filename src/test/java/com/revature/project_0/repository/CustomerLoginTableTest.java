package com.revature.project_0.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.project_0.repository.model.*;

public class CustomerLoginTableTest {
	private CustomerLoginModel[] customerLogins;
	private CustomerLoginTable customerLoginTable;
	private static final String USERNAME = "John83";
	private static final String USERNAME_2 = "1234";
	private static final String PASSWORD_2 = "apple5";
	
	@Before
	public void init() {
		customerLogins = new CustomerLoginModel[] {
				CustomerLoginModel.getBuilder()
					.withUsername(USERNAME)
					.build(),
					CustomerLoginModel.getBuilder()
					.withUsername(USERNAME)
					.withPassword(PASSWORD_2)
					.build(),
		};
		customerLoginTable = new CustomerLoginTable();
	}
	
	
	@Test
	public void addRecordSucceeds() {
		assertTrue(customerLoginTable.addRecord(USERNAME, customerLogins[0]));
	}
	
	@Test
	public void deleteRecordSucceeds() {
		customerLoginTable.addRecord(USERNAME, customerLogins[0]);
		assertTrue(customerLoginTable.deleteRecord(USERNAME));
	}
	
	@Test
	public void deleteRecordFails() {
		customerLoginTable.addRecord(USERNAME, customerLogins[0]);
		assertFalse(customerLoginTable.deleteRecord(USERNAME_2));
	}
	
	@Test
	public void selectRecord() {
		customerLoginTable.addRecord(USERNAME_2, customerLogins[1]);
		assertEquals(PASSWORD_2, customerLoginTable.selectRecord(USERNAME_2).getPassword());
	}
	
	@Test
	public void verifyUsernameAlreadyTaken() {
		customerLoginTable.addRecord(USERNAME, customerLogins[0]);
		customerLoginTable.addRecord(USERNAME_2, customerLogins[1]);
		String duplicateUsername = USERNAME;
		assertNotEquals(null, customerLoginTable.selectRecord(duplicateUsername));
	}
}
