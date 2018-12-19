package com.revature.project_0.entity;

import com.revature.project_0.entity.actions.CustomerSelfIdentify;
import com.revature.project_0.repository.Repository;

public class Customer {
	private CustomerSelfIdentify customerSelfIdentify;
	private Repository repository;

	public Customer(Repository repository) {
		this.repository = repository;
		reset();
	}
	
	private void reset() {
		customerSelfIdentify = new CustomerSelfIdentify(repository);
	}

	public CustomerSelfIdentify getCustomerSelfIdentify() {
		return customerSelfIdentify;
	}
	
	public void signOut() {
		reset();
	}
	
	public boolean isSignedIn() {
		return customerSelfIdentify.getUserName() != null;
	}
}
