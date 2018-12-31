package com.revature.project_0.repository;

import com.revature.project_0.repository.model.CustomerLoginModel;

public class CustomerLoginTable extends AbstractTable<String, CustomerLoginModel> {
	public boolean addRecord(String username, CustomerLoginModel customerLoginModel) {
		return super.addRecord(username, customerLoginModel);
	}
	
	public boolean deleteRecord(String username) {
		return super.deleteRecord(username);
	}
	
	public CustomerLoginModel selectRecord(String username) {
		return super.selectRecord(username);
	}
	
	@Override
	String incrementPrimaryKey(String key) {
		return key;
	}
	
	@Override
	String firstPrimaryKey() {
		return null;
	}
}
