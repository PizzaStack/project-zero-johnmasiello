package com.revature.project_0.entity;

import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerLoginServiceTest {
	@Test
	public void createCustomerLogin() {
		CustomerLoginService c = new CustomerLoginService();
	}
	
	@Test
	public void customerLoginServiceSetUserName() {
		CustomerLoginService service = new CustomerLoginService();
		
	}
	
	@Test
	public void customerHasRegistered() {
		CustomerLoginService service = new CustomerLoginService();
		assertEquals(true, service.isRegistered());
	}
}
