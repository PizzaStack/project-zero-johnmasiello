package com.revature.project_0.entity;

import com.revature.project_0.entity.actions.EmployeeManageApplication;
import com.revature.project_0.entity.actions.EmployeeSelfIdentify;
import com.revature.project_0.entity.actions.EmployeeViewInfos;
import com.revature.project_0.repository.Repository;

public class Employee {
	private EmployeeViewInfos viewInfos;
	private EmployeeSelfIdentify selfIdentify;
	private EmployeeManageApplication manageApplication;

	public Employee(Repository repository) {
		viewInfos = new EmployeeViewInfos(repository);
		selfIdentify = new EmployeeSelfIdentify();
		manageApplication = new EmployeeManageApplication(repository);
	}

	public EmployeeViewInfos getViewInfos() {
		return viewInfos;
	}

	public EmployeeSelfIdentify getSelfIdentify() {
		return selfIdentify;
	}

	public EmployeeManageApplication getManageApplication() {
		return manageApplication;
	}
	
	
}
