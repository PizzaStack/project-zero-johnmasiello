package com.revature.project_0.repository;

import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.revature.project_0.repository.dao.AccountInfoDao;
import com.revature.project_0.repository.dao.ApplicationDao;
import com.revature.project_0.repository.dao.CustomerLoginDao;
import com.revature.project_0.repository.dao.PersonalInfoDao;
import com.revature.project_0.repository.model.*;
import com.revature.project_0.util.Util;

public class Repository {
	private AccountInfoTable accountInfoTable;
	private ApplicationTable applicationTable;
	private CustomerLoginTable customerLoginTable;
	private PersonalInfoTable personalInfoTable;
	private CustomerLoginDao customerLoginDao;
	private PersonalInfoDao personalInfoDao;
	private ApplicationDao applicationDao;
	private AccountInfoDao accountInfoDao;
	
	private CustomerLoginModel loginValidationHelper;
	
	public Repository() {
		makeDaos();
		loadTables();
		loginValidationHelper = new CustomerLoginModel.Builder().build();
	}
	
	private void makeDaos() {
		customerLoginDao = new CustomerLoginDao();
		personalInfoDao = new PersonalInfoDao();
		applicationDao = new ApplicationDao();
		accountInfoDao = new AccountInfoDao();
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
				customerLoginDao.queryIsUsernameUnique(requestedUsername);
	}
	
	public boolean isValidPassword(String requestedPassword) {
		return loginValidationHelper.validateNewPassword(requestedPassword);
	}
	
	@Nullable
	public CustomerLoginModel createNewCustomerUponValidUsernameAndPassword(String username,
			String password) {
		return customerLoginDao.insertNewSignUp(username, password);
	}
	
	// Then you fill out personal information, before...
	@Nullable
	public PersonalInfoModel createOrUpdateNewPersonalInformation(@NotNull PersonalInfoModel personalInfoModel) {
		return personalInfoDao.upsert(personalInfoModel);
	}
	
	// You fill out application, which waits in a pool ...
	@Nullable
	public ApplicationModel createNewApplication(ApplicationModel applicationModel) {
		return applicationDao.insertNewApplication(applicationModel);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Returning Customer side
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public CustomerLoginModel authenticateCustomer(@NotNull String username, @NotNull String password) {
		return customerLoginDao.queryLogin(username, password);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Bank facing side
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	// until The bank employee approves/Disapproves, which generates an accountInfoModel
	@Nullable
	public ApplicationModel rejectApplication(@NotNull ApplicationModel applicationModel, @NotNull String empId) {
		return applicationDao.deleteApplication((int) applicationModel.getApplicationId()) ? applicationModel :
			null;
	}
	
	@Nullable
	private PersonalInfoModel trackJointCustomerBySSN(String ssn) {
		return personalInfoDao.queryPersonalInfoBySSN(ssn);
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
		return accountInfoDao.approveApplication(application, approverId);
	}
	
	@Nullable
	public ApplicationModel getApplicationModel(long applicationId) {
		return applicationDao.queryApplicationById((int) applicationId);
	}
	
	@Nullable
	public AccountInfoModel approveAccount(long accountId, @NotNull String approveId) {
		AccountInfoModel account = getAccountInfo(accountId);
		if (account != null && account.getStatus() != AccountStatus.CLOSED) {
			accountInfoDao.updateOnAccountApproved((int) accountId, AccountStatus.APPROVED, approveId);
		}
		return account;
	}
	
	@Nullable
	public AccountInfoModel denyAccount(long accountId, @NotNull String approveId) {
		AccountInfoModel account = getAccountInfo(accountId);
		if (account != null && account.getStatus() != AccountStatus.CLOSED) {
			accountInfoDao.updateOnAccountApproved((int) accountId, AccountStatus.DENIED, approveId);
		}
		return account;
	}
	
	public Collection<ApplicationModel> getAllApplications() {
		return applicationDao.queryApplicationsForAllCustomers();
	}
	
	public Collection<AccountInfoModel> getAllAccounts() {
		return accountInfoDao.queryAllAccountsForAllCustomers();
	}
	
	public Collection<PersonalInfoModel> getAllCustomersPersonalInformation() {
		return personalInfoDao.queryPersonalInfoForAllCustomers();
	}
	
	@Nullable
	public AccountInfoModel cancelAccount(long accountId) {
		AccountInfoModel account = getAccountInfo(accountId);
		if (account != null) {
			account.setStatus(AccountStatus.CLOSED);
			account.setDateClosed(Util.getCurrentDate());
			return accountInfoDao.deleteAccountInfo((int) accountId) ? account : null;
		}
		return null;
	}
	
	public List<ApplicationModel> getAllAssociatedApplications(long customerId) {
		return applicationDao.queryApplicationsByCustomerId((int) customerId);
	}
	
	public List<AccountInfoModel> getAllAssociatedAccounts(long customerId) {
		return accountInfoDao.queryAccountInfoByCustomerId((int) customerId);
	}
	
	public PersonalInfoModel getPersonalInformation(long customerId) {
		return personalInfoDao.queryPersonalInfoByCustomerId((int) customerId);
	}
	
	@Nullable
	public AccountInfoModel getAccountInfo(long accountId) {
		return accountInfoDao.queryAccountInfoById((int) accountId);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Transactions
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean updateBalanceOnDepositOrWithdrawal(int accountId, double newBalance) {
		return accountInfoDao.updateBalance(accountId, newBalance);
	}
	
	public boolean updateBalancesOnTransfer(int accountId_1, double newBalance_1,
			int accountId_2, double newBalance_2) {
		return accountInfoDao.updateMultipleBalancesInSingleTransaction(accountId_1, newBalance_1, 
				accountId_2, newBalance_2);
	}
}