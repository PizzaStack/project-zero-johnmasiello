 package com.revature.project_0.entity.actions;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.PersonalInfoModel;

public class EmployeeViewInfos implements ViewInfos {
	private Repository repository;

	public EmployeeViewInfos(Repository repository) {
		this.repository = repository;
	}
	
	public String viewCustomerApplications(long cutomerId) {
		StringBuilder strBuilder = new StringBuilder();
		repository.getAllAssociatedApplications(cutomerId).
			forEach(($) -> {strBuilder.append($).append("\n\n");});
		return strBuilder.toString();
	}
	
	public String viewCustomerAccounts(long customerId) {
		StringBuilder strBuilder = new StringBuilder();
		repository.getAllAssociatedAccounts(customerId)
			.forEach(($) -> {strBuilder.append($).append("\n\n");});
		return strBuilder.toString();
	}
	
	public String viewCustomerPersonalInformation(long customerId) {
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
}
