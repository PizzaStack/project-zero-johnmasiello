package com.revature.project_0.entity;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.CustomerFriendlyAccount;
import com.revature.project_0.repository.model.CustomerLoginModel;
import com.revature.project_0.repository.model.PersonalInfoModel;
import com.revature.project_0.util.Util;

public class Customer {
	final private Repository repository;
	final private FundsTransactionManager fundsTransactionManager;
	private List<AccountInfoModel> cachedAccounts;
	private CustomerLoginModel customerLoginModel;
	private PersonalInfoModel personalInfoModel;
	private ApplicationModel applicationModel;

	public Customer(Repository repository) {
		this.repository = repository;
		fundsTransactionManager = new FundsTransactionManager(repository);
		reset();
	}
	
	private void reset() {
		cachedAccounts = null;
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
		cachedAccounts = repository.getAllAssociatedAccounts(customerId);
		return !cachedAccounts.isEmpty();
	}
	
	@Nullable
	public AccountInfoModel pickAccountByAccountId(long id) {
		for (AccountInfoModel a : cachedAccounts) {
			if (a.getAccountId() == id)
				return a;
		}
		return null;
	}
	
	public FundsTransactionManager getFundsTransactionManager() {
		return fundsTransactionManager;
	}
	
	public String viewAllAccounts(long customerId) {
		List<AccountInfoModel> accounts = repository.getAllAssociatedAccounts(customerId);
		List<CustomerFriendlyAccount> wrappedAccounts = new ArrayList<>();
		accounts.forEach(($) -> {wrappedAccounts.add(new CustomerFriendlyAccount($));});
		return Util.printAllRecords(wrappedAccounts);
	}
	
	public String viewAllAccounts() {
		List<CustomerFriendlyAccount> wrappedAccounts = new ArrayList<>();
		cachedAccounts.forEach(($) -> {wrappedAccounts.add(new CustomerFriendlyAccount($));});
		return Util.printAllRecords(wrappedAccounts);
	}
	
	public String viewAllAcountsAsEnumerated() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < cachedAccounts.size(); i++) {
			if (i == 0)
				str.append(i + 1).append(')').append(Util.PRINT_COLUMN_DELIMITER)
				.append(new CustomerFriendlyAccount(cachedAccounts.get(0)));
			else
				str.append(Util.PRINT_ROW_DELIMITER).append(cachedAccounts.get(i));
		}
		return str.toString();
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
		personalInfoModel = repository.getPersonalInformation(id);
		return personalInfoModel != null;
	}
	
	public boolean createOrUpdatePersonalInfo(PersonalInfoModel personalInfo) {
		personalInfoModel = repository.createOrUpdateNewPersonalInformation(personalInfo);
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
