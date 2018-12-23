package com.revature.project_0.entity;

import org.jetbrains.annotations.Nullable;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;

public class Administrator extends Employee{
	final private FundsTransactionManager fundsTransactionManager;
	
	public Administrator(Repository repository) {
		super(repository);
		fundsTransactionManager = new FundsTransactionManager(repository);
	}
	
	@Nullable
	public AccountInfoModel approveAccount(long accountId) {
		return getRepository().approveAccount(accountId, getEmployeeId());
	}
	
	@Nullable
	public AccountInfoModel denyAccount(long accountId) {
		return getRepository().denyAccount(accountId, getEmployeeId());
	}
	
	@Nullable
	public AccountInfoModel cancelAccount(long accountId) {
		return getRepository().cancelAccount(accountId);
	}
	
	@Nullable
	public AccountInfoModel getAccountById(long accountId) {
		return getRepository().getAccountInfo(accountId);
	}

	public FundsTransactionManager getFundsTransactionManager() {
		return fundsTransactionManager;
	}
}
