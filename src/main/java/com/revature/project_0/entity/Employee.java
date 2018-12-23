package com.revature.project_0.entity;

import static com.revature.project_0.repository.TableOutcome.FAIL_TO_UPDATE;
import static com.revature.project_0.repository.TableOutcome.NO_SUCH_RECORD;
import static com.revature.project_0.repository.TableOutcome.OK;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.PersonalInfoModel;
import com.revature.project_0.util.Util;

public class Employee {
	private Repository repository;
	private AccountInfoModel newAccountCreated;
	private String employeeId;
	private int errorCode;

	public Employee(Repository repository) {
		this.repository = repository;
	}
	
	Repository getRepository() {
		return repository;
	}
	
	/*
	 * View Infos
	 */
	private String viewCustomerApplications(long customerId) {
		return Util.printAllRecords(
				repository.getAllAssociatedApplications(customerId));
	}
	
	private String viewCustomerAccounts(long customerId) {
		return Util.printAllRecords(
				repository.getAllAssociatedAccounts(customerId));
	}
	
	private String viewCustomerPersonalInformation(long customerId) {
		PersonalInfoModel m = repository.getPersonalInformation(customerId);
		return m != null ? m.toString() : "";
	}
	
	public String viewAllAssociatedCustomerInfo(long customerId) {
		 return new StringBuilder(viewCustomerApplications(customerId))
				 .append(viewCustomerAccounts(customerId))
				 .append(viewCustomerPersonalInformation(customerId))
				 .toString();
	}
	
	public String viewAllApplications() {
		return Util.printAllRecords(repository.getAllApplications());
	}
	
	public String viewAllAccounts() {
		return Util.printAllRecords(repository.getAllAccounts());
	}
	
	public String viewAllCustomersPersonalInformation() {
		return Util.printAllRecords(repository.getAllCustomersPersonalInformation());
	}
	
	/*
	 * Employee Tracking
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	public void resetEmployeeId() {
		employeeId = null;
	}
	
	/*
	 * Manage Applications
	 */
	public boolean approveApplication(long appId, String empId) {
		errorCode = OK;
		ApplicationModel application = repository.getApplicationModel(appId);
		if (application == null) {
			errorCode = NO_SUCH_RECORD;
			return false;
		}
		newAccountCreated = repository.approveApplication(application, empId);
		if (newAccountCreated == null) {
			errorCode = FAIL_TO_UPDATE;
			return false;
		}
		return true;
	}
	
	public boolean denyApplication(long appId, String empId) {
		errorCode = OK;
		ApplicationModel application = repository.getApplicationModel(appId);
		if (application == null) {
			errorCode = NO_SUCH_RECORD;
			return false;
		} else if (repository.rejectApplication(application, empId) == null) {
			errorCode = FAIL_TO_UPDATE;
			return false;
		}
		return true;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
	public AccountInfoModel getNewAccount() {
		return newAccountCreated;
	}
}
