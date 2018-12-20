package com.revature.project_0.repository;

import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.revature.project_0.repository.model.*;
import com.revature.project_0.util.Util;

public class Repository {
	private AccountInfoTable accountInfoTable;
	private ApplicationTable applicationTable;
	private CustomerLoginTable customerLoginTable;
	private PersonalInfoTable personalInfoTable;
	
	private CustomerLoginModel loginValidationHelper;
	
	public Repository() {
		loadTables();
		loginValidationHelper = new CustomerLoginModel.Builder().build();
	}
	
	private void loadTables() {
		accountInfoTable 	= new AccountInfoTable();
		applicationTable 	= new ApplicationTable();
		customerLoginTable 	= new CustomerLoginTable();
		personalInfoTable	= new PersonalInfoTable();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Prospective Customer facing side
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isValidUsername(String requestedUsername) {
		return loginValidationHelper.validateNewUsername(requestedUsername);
	}
	
	// First Create a username and password to get a customer id
	public boolean isValidAndUniqueUsername(String requestedUsername) {
		return loginValidationHelper.validateNewUsername(requestedUsername) &&
				customerLoginTable.selectRecord(requestedUsername) == null;
	}
	
	public boolean isValidPassword(String requestedPassword) {
		return loginValidationHelper.validateNewPassword(requestedPassword);
	}
	
	@Nullable
	public CustomerLoginModel createNewCustomerUponValidUsernameAndPassword(String username,
			String password) {
		final long customerId = personalInfoTable.generateNextPrimaryKey();
		CustomerLoginModel newCustomer = new CustomerLoginModel.Builder()
				.withUsername(username)
				.withPassword(password)
				.withCustomerId(customerId)
				.build();
		return customerLoginTable.addRecord(username, newCustomer) ? newCustomer : null;
	}
	
	// Then you fill out personal information, before...
	@Nullable
	public PersonalInfoModel createNewPersonalInformation(@NotNull PersonalInfoModel personalInfoModel) {
		return personalInfoTable.addRecord(personalInfoModel.getCustomerId(), personalInfoModel) ?
				personalInfoModel : null;
	}
	
	// You fill out application, which waits in a pool ...
	@Nullable
	public ApplicationModel createNewApplication(ApplicationModel applicationModel) {
		final long appId = applicationTable.generateNextPrimaryKey();
		applicationModel.setApplicationId(appId);
		return applicationTable.addRecord(appId, applicationModel) ? applicationModel :
			null;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Returning Customer side
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public CustomerLoginModel authenticateCustomer(@NotNull String username, @NotNull String password) {
		CustomerLoginModel login = customerLoginTable.selectRecord(username);
		return login != null && password.equals(login.getPassword()) ? login : null;
	}
	
	public PersonalInfoModel getPersonalRecord(long customerId) {
		return personalInfoTable.selectRecord(customerId);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Bank facing side
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	// until The bank employee approves/Disapproves, which generates an accountInfoModel
	@Nullable
	public ApplicationModel rejectApplication(@NotNull ApplicationModel applicationModel, @NotNull String empId) {
		return applicationTable.deleteRecord(applicationModel.getApplicationId()) ? applicationModel :
			null;
	}
	
	@Nullable
	public AccountInfoModel approveApplication(@NotNull ApplicationModel application, @NotNull String empId) {
		final long accountId = accountInfoTable.generateNextPrimaryKey();
		AccountInfoModel accountInfoModel = new AccountInfoModel.Builder()
				.withAccountId(accountId)
				.withBalance(0.00)
				.withCustomerId(application.getCustomerId())
				.withJointCustomerId(application.getJointCustomerId())
				.withDateOpened(Util.getCurrentDate())
				.withType(application.getType())
				.withStatus(AccountStatus.OPENED)
				.withEmpId(empId)
				.build();
		return accountInfoTable.addRecord(accountId, accountInfoModel) ? accountInfoModel : 
			null;
	}
	
	@Nullable
	public ApplicationModel getApplicationModel(long applicationId) {
		return applicationTable.selectRecord(applicationId);
	}
	
	@Nullable
	public AccountInfoModel approveAccount(long accountId, @NotNull String adminId) {
		AccountInfoModel account = accountInfoTable.selectRecord(accountId);
		if (account != null) {
			account.setStatus(AccountStatus.APPROVED);
			account.setAdminId(adminId);
		}
		return account;
	}
	
	@Nullable
	public AccountInfoModel denyAccount(long accountId, @NotNull String adminId) {
		AccountInfoModel account = accountInfoTable.selectRecord(accountId);
		if (account != null) {
			account.setStatus(AccountStatus.DENIED);
			account.setAdminId(adminId);
		}
		return account;
	}
	
	public Collection<ApplicationModel> getAllApplications() {
		return applicationTable.getTable().values();
	}
	
	public Collection<AccountInfoModel> getAllAccounts() {
		return accountInfoTable.getTable().values();
	}
	
	public AccountInfoModel cancelAccount(long accountId) {
		AccountInfoModel account = accountInfoTable.selectRecord(accountId);
		account.setStatus(AccountStatus.CLOSED);
		account.setDateClosed(Util.getCurrentDate());
		return accountInfoTable.deleteRecord(accountId) ? account : null;
	}
	
	public List<ApplicationModel> getAllAssociatedApplications(long customerId) {
		return applicationTable.getAllAssociatedApplications(customerId);
	}
	
	public PersonalInfoModel getPersonalInformation(long customerId) {
		return personalInfoTable.selectRecord(customerId);
	}
	
	public List<AccountInfoModel> getAllAssociatedAccounts(long customerId) {
		return accountInfoTable.getAllAssociatedAccounts(customerId);
	}
	
	@Nullable
	public AccountInfoModel getAccountInfo(long accountId) {
		return accountInfoTable.selectRecord(accountId);
	}

	AccountInfoTable getAccountInfoTable() {
		return accountInfoTable;
	}

	ApplicationTable getApplicationTable() {
		return applicationTable;
	}

	CustomerLoginTable getCustomerLoginTable() {
		return customerLoginTable;
	}

	PersonalInfoTable getPersonalInfoTable() {
		return personalInfoTable;
	}
}