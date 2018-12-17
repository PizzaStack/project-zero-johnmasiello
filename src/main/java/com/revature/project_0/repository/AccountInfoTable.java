package com.revature.project_0.repository;

import java.util.LinkedList;
import java.util.List;

import com.revature.project_0.repository.model.AccountInfoModel;

class AccountInfoTable extends SimpleTable<AccountInfoModel> {
	public boolean addRecord(long accountId, AccountInfoModel accountInfoModel) {
		return super.addRecord(accountId, accountInfoModel);
	}
	
	public boolean deleteRecord(long accountId) {
		return super.deleteRecord(accountId);
	}
	
	public AccountInfoModel selectRecord(long accountId) {
		return super.selectRecord(accountId);
	}
	
	public List<AccountInfoModel> getAllAssociatedAccounts(long customerId) {
		List<AccountInfoModel> accounts = new LinkedList<>();
		getTable().values().forEach(($) -> 
			{if ($.getCustomerId() == customerId || $.getJointCustomerId() == customerId)
				accounts.add($);
			});
		return accounts;
	}
}
