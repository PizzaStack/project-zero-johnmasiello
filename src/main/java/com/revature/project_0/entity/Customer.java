package com.revature.project_0.entity;

import java.util.List;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.CustomerLoginModel;
import com.revature.project_0.repository.model.PersonalInfoModel;

public class Customer {
	final private Repository repository;
	final private FundsTransactionManager fundsTransactionManager;
	private List<AccountInfoModel> listOfAccounts;
	private CustomerLoginModel customerLoginModel;
	private PersonalInfoModel personalInfoModel;
	private ApplicationModel applicationModel;

	public Customer(Repository repository) {
		this.repository = repository;
		fundsTransactionManager = new FundsTransactionManager(repository);
		reset();
	}
	
	private void reset() {
		listOfAccounts = null;
		customerLoginModel = null;
		personalInfoModel = null;
		applicationModel = null;
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
		customerLoginModel = repository.createNewCustomerUponValidUsernameAndPassword(
				username, password);
		return customerLoginModel != null;
	}
	
	public String getUserName() {
		return customerLoginModel != null ? customerLoginModel.getUsername() : null;
	}
	
	public long getCustomerId() {
		return customerLoginModel != null ? customerLoginModel.getCustomerId() : CustomerLoginModel.NO_ID;
	}
	
	public boolean signInSuccessful(String username, String password) {
		customerLoginModel = repository.authenticateCustomer(username, password);
		return customerLoginModel != null;
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
	
	public FundsTransactionManager getFundsTransactionManager() {
		return fundsTransactionManager;
	}
	
	/*
	 * Manage Applications
	 */
	
	public boolean createApplication(ApplicationModel application) {
		return repository.createNewApplication(application) != null;
	}

	public ApplicationModel getApplication() {
		return applicationModel;
	}

	public void setApplicationModel(ApplicationModel applicationModel) {
		this.applicationModel = applicationModel;
	}
	
	public boolean hasPersonalRecordOnFile() {
		if (personalInfoModel != null)
			return true;
		long id = getCustomerId();
		if (id == CustomerLoginModel.NO_ID)
			return false;
		personalInfoModel = repository.getPersonalRecord(id);
		return personalInfoModel != null;
	}
	
	public boolean createPersonalInfo(PersonalInfoModel personalInfo) {
		personalInfoModel = repository.createNewPersonalInformation(personalInfo);
		return personalInfo != null;
	}

	public PersonalInfoModel getPersonalInfoModel() {
		return personalInfoModel;
	}

	public void setPersonalInfoModel(PersonalInfoModel personalInfoModel) {
		this.personalInfoModel = personalInfoModel;
	}

	public void resetPersonalInfo() {
		this.personalInfoModel = null;
	}
}
