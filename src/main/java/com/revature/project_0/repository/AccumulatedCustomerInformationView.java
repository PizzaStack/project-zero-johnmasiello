package com.revature.project_0.repository;

import java.util.List;

import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.PersonalInfoModel;

public class AccumulatedCustomerInformationView {
	private final PersonalInfoModel personalInfo;
	private final List<ApplicationModel> accountApplications;
	private final List<AccountInfoModel> accounts;
	
	public AccumulatedCustomerInformationView(PersonalInfoModel personalInfo,
			List<ApplicationModel> accountApplications, List<AccountInfoModel> accounts) {
		this.personalInfo = personalInfo;
		this.accountApplications = accountApplications;
		this.accounts = accounts;
	}

	public PersonalInfoModel getPersonalInfo() {
		return personalInfo;
	}

	public List<ApplicationModel> getAccountApplications() {
		return accountApplications;
	}

	public List<AccountInfoModel> getAccounts() {
		return accounts;
	}
}
