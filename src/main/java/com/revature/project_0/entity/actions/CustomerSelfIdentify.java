package com.revature.project_0.entity.actions;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.CustomerLoginModel;

public class CustomerSelfIdentify implements SelfIdentify {
	private Repository repository;
	private CustomerLoginModel customerInfoModel;

	public CustomerSelfIdentify(Repository repository) {
		this.repository = repository;
	}
	
	public boolean isValidUsername(String requestedUsername) {
		return repository.isValidUsername(requestedUsername);
	}
	
	public boolean isUniqueUsername(String requestedUsername) {
		return repository.isValidAndUniqueUsername(requestedUsername);
	}
	
	public boolean isValidPassword(String requestedPassword) {
		return repository.isValidPassword(requestedPassword);
	}
	
	public boolean createNewCustomer(String username, String password) {
		customerInfoModel = repository.createNewCustomerUponValidUsernameAndPassword(
				username, password);
		return customerInfoModel != null;
	}
	
	public String getUserName() {
		return customerInfoModel != null ? customerInfoModel.getUsername() : null;
	}
	
	public boolean signInSuccessful(String username, String password) {
		customerInfoModel = repository.authenticateCustomer(username, password);
		return customerInfoModel != null;
	}
}
