package com.revature.project_0.repository;

import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.revature.project_0.repository.model.*;
import com.revature.project_0.util.Util;

public class MockRepository extends Repository {
	private AccountInfoTable accountInfoTable;
	private ApplicationTable applicationTable;
	private CustomerLoginTable customerLoginTable;
	private PersonalInfoTable personalInfoTable;
	
	private CustomerLoginModel loginValidationHelper;
	
	public MockRepository() {
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
	public PersonalInfoModel createOrUpdateNewPersonalInformation(@NotNull PersonalInfoModel personalInfoModel) {
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
	private PersonalInfoModel trackJointCustomerBySSN(String SSN) {
		return personalInfoTable.fetchCustomerBySSN(SSN);
	}

	public boolean crossReferenceJointCustomerSSN(@NotNull ApplicationModel application) {
		PersonalInfoModel person = trackJointCustomerBySSN(application.getJointCustomerSSN());
		if (person == null)
			return false;
		application.setJointCustomerId(person.getCustomerId());
		return true;
	}
	
	@Nullable
	public AccountInfoModel approveApplication(@NotNull ApplicationModel application, @NotNull String approverId) {
		final long accountId = accountInfoTable.generateNextPrimaryKey();
		AccountInfoModel accountInfoModel = new AccountInfoModel.Builder()
				.withAccountId(accountId)
				.withAccountName(application.getAccountName())
				.withBalance(0.00)
				.withCustomerId(application.getCustomerId())
				.withJointCustomerId(application.getJointCustomerId())
				.withDateOpened(Util.getCurrentDate())
				.withType(application.getType())
				.withStatus(AccountStatus.OPENED)
				.withAppAproverId(approverId)
				.build();
		if (accountInfoTable.addRecord(accountId, accountInfoModel)) {
			applicationTable.deleteRecord(application.getApplicationId());
			return accountInfoModel;
		}
		return null;
	}
	
	@Nullable
	public ApplicationModel getApplicationModel(long applicationId) {
		return applicationTable.selectRecord(applicationId);
	}
	
	@Nullable
	public AccountInfoModel approveAccount(long accountId, @NotNull String approverId) {
		AccountInfoModel account = accountInfoTable.selectRecord(accountId);
		if (account != null && account.getStatus() != AccountStatus.CLOSED) {
			account.setStatus(AccountStatus.APPROVED);
			account.setAcctApproverId(approverId);
		}
		return account;
	}
	
	@Nullable
	public AccountInfoModel denyAccount(long accountId, @NotNull String approverId) {
		AccountInfoModel account = accountInfoTable.selectRecord(accountId);
		if (account != null && account.getStatus() != AccountStatus.CLOSED) {
			account.setStatus(AccountStatus.DENIED);
			account.setAcctApproverId(approverId);
		}
		return account;
	}
	
	public Collection<ApplicationModel> getAllApplications() {
		return applicationTable.getTable().values();
	}
	
	public Collection<AccountInfoModel> getAllAccounts() {
		return accountInfoTable.getTable().values();
	}
	
	public Collection<PersonalInfoModel> getAllCustomersPersonalInformation() {
		return personalInfoTable.getTable().values();
	}
	
	@Nullable
	public AccountInfoModel cancelAccount(long accountId) {
		AccountInfoModel account = accountInfoTable.selectRecord(accountId);
		account.setStatus(AccountStatus.CLOSED);
		account.setDateClosed(Util.getCurrentDate());
		return accountInfoTable.deleteRecord(accountId) ? account : null;
	}
	
	public List<ApplicationModel> getAllAssociatedApplications(long customerId) {
		return applicationTable.getAllAssociatedApplications(customerId);
	}
	
	public List<AccountInfoModel> getAllAssociatedAccounts(long customerId) {
		return accountInfoTable.getAllAssociatedAccounts(customerId);
	}
	
	public PersonalInfoModel getPersonalInformation(long customerId) {
		return personalInfoTable.selectRecord(customerId);
	}
	
	@Override
	public boolean updateBalanceOnDepositOrWithdrawal(int accountId, double newBalance) {
		return true;
	}

	@Override
	public boolean updateBalancesOnTransfer(int accountId_1, double newBalance_1, int accountId_2,
			double newBalance_2) {
		return true;
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