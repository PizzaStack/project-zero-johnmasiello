package com.revature.project_0.entity;

import org.jetbrains.annotations.Nullable;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.AccountInfoModel;

public class Administrator extends Employee{
	public Administrator(Repository repository) {
		super(repository);
	}
	
	@Nullable
	public AccountInfoModel approveAccount(long accountId) {
		return getRepository().approveAccount(accountId, getEmployeeId());
	}
	
	@Nullable
	public AccountInfoModel denyAccount(long accountId) {
		return getRepository().approveAccount(accountId, getEmployeeId());
	}
	
	@Nullable
	public AccountInfoModel cancelAccount(long accountId) {
		return getRepository().cancelAccount(accountId);
	}
}
