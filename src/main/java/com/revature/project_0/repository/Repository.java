package com.revature.project_0.repository;

import com.revature.project_0.repository.model.*;

public class Repository {
	private AccountInfoTable accountInfoTable;
	private ApplicationTable applicationTable;
	private CustomerLoginTable customerLoginTable;
	private PersonalInfoTable personalInfoTable;
	
	public Repository() {
		loadTables();
	}
	
	private void loadTables() {
		accountInfoTable 	= new AccountInfoTable();
		applicationTable 	= new ApplicationTable();
		customerLoginTable 	= new CustomerLoginTable();
		personalInfoTable	= new PersonalInfoTable();
	}
}
