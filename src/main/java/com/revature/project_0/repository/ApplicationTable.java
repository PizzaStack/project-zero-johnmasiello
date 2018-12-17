package com.revature.project_0.repository;

import java.util.LinkedList;
import java.util.List;

import com.revature.project_0.repository.model.ApplicationModel;

public class ApplicationTable extends AbstractTable<Long, ApplicationModel> {
	public boolean addRecord(long applicationId, ApplicationModel applicationModel) {
		return super.addRecord(applicationId, applicationModel);
	}
	
	public boolean deleteRecord(long applicationId) {
		return super.deleteRecord(applicationId);
	}
	
	public ApplicationModel selectRecord(long applicationId) {
		return super.selectRecord(applicationId);
	}
	
	public List<ApplicationModel> getAllAssociatedApplications(long customerId) {
		List<ApplicationModel> applications = new LinkedList<>();
		getTable().values().forEach(($) -> 
			{if ($.getCustomerId() == customerId || $.getJointCustomerId() == customerId)
				applications.add($);
			});
		return applications;
	}
	
	@Override
	Long incrementPrimaryKey(Long key) {
		return ++key;
	}
	
	@Override
	Long firstPrimaryKey() {
		return 0l;
	}
}
