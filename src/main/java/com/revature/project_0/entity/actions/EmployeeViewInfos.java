package com.revature.project_0.entity.actions;

import com.revature.project_0.repository.Repository;

public class EmployeeViewInfos implements ViewInfos {
	private Repository repository;

	public EmployeeViewInfos(Repository repository) {
		this.repository = repository;
	}
	
	public String viewCustomerApplications(long cutomerId) {
		return null;
	}
}
