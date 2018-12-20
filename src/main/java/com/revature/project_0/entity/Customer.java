package com.revature.project_0.entity;

import java.util.List;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.CustomerLoginModel;

public class Customer {
	private Repository repository;
	private List<AccountInfoModel> listOfAccounts;
	private CustomerLoginModel customerInfoModel;

	public Customer(Repository repository) {
		this.repository = repository;
		reset();
	}
	
	private void reset() {
		customerInfoModel = null;
		listOfAccounts = null;
	}
	
	/**
	 * Authentication
	 */
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
	
	public void signOut() {
		reset();
	}
	
	public boolean isSignedIn() {
		return getUserName() != null;
	}
	
	/*
	 * Manage Accounts
	 */	
	public boolean fetchAccounts(long customerId) {
		listOfAccounts = repository.getAllAssociatedAccounts(customerId);
		return !listOfAccounts.isEmpty();
	}
	
	public List<AccountInfoModel> getAccounts() {
		return listOfAccounts;
	}
}
