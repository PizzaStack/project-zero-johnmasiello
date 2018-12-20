package com.revature.project_0.entity;

import static com.revature.project_0.repository.TableOutcome.FAIL_TO_UPDATE;
import static com.revature.project_0.repository.TableOutcome.NO_SUCH_RECORD;
import static com.revature.project_0.repository.TableOutcome.OK;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.PersonalInfoModel;

public class Employee {
	private Repository repository;
	private String employeeId;
	private int errorCode;

	public Employee(Repository repository) {
		this.repository = repository;
	}
	
	/*
	 * View Infos
	 */
	private String viewCustomerApplications(long cutomerId) {
		StringBuilder strBuilder = new StringBuilder();
		repository.getAllAssociatedApplications(cutomerId).
			forEach(($) -> {strBuilder.append($).append("\n\n");});
		return strBuilder.toString();
	}
	
	private String viewCustomerAccounts(long customerId) {
		StringBuilder strBuilder = new StringBuilder();
		repository.getAllAssociatedAccounts(customerId)
			.forEach(($) -> {strBuilder.append($).append("\n\n");});
		return strBuilder.toString();
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
		StringBuilder strBuilder = new StringBuilder();
		repository.getAllApplications()
			.forEach(($) -> {strBuilder.append($).append("\n\n");});
		return strBuilder.toString();
	}
	
	public String viewAllAccounts() {
		StringBuilder strBuilder = new StringBuilder();
		repository.getAllAccounts()
			.forEach(($) -> {strBuilder.append($).append("\n\n");});
		return strBuilder.toString();
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
		ApplicationModel applicationModel = repository.getApplicationModel(appId);
		if (applicationModel == null) {
			errorCode = NO_SUCH_RECORD;
			return false;
		} else if (repository.approveApplication(applicationModel, empId) == null) {
			errorCode = FAIL_TO_UPDATE;
			return false;
		} return true;
	}
	
	public boolean denyApplication(long appId, String empId) {
		errorCode = OK;
		ApplicationModel applicationModel = repository.getApplicationModel(appId);
		if (applicationModel == null) {
			errorCode = NO_SUCH_RECORD;
			return false;
		} else if (repository.rejectApplication(applicationModel, empId) == null) {
			errorCode = FAIL_TO_UPDATE;
			return false;
		} return true;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
