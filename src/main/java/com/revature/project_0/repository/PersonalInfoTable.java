package com.revature.project_0.repository;

import java.util.Collection;

import org.jetbrains.annotations.NotNull;

import com.revature.project_0.repository.model.PersonalInfoModel;

public class PersonalInfoTable extends AbstractTable<Long, PersonalInfoModel> {
	public boolean addRecord(long customerId, PersonalInfoModel personalInfoModel) {
		return super.addRecord(customerId, personalInfoModel);
	}
	
	public boolean deleteRecord(long customerId) {
		return super.deleteRecord(customerId);
	}
	
	public PersonalInfoModel selectRecord(long customerId) {
		return super.selectRecord(customerId);
	}
	
	@Override
	Long incrementPrimaryKey(Long key) {
		return ++key;
	}
	
	@Override
	Long firstPrimaryKey() {
		return 0l;
	}
	
	public PersonalInfoModel fetchCustomerBySSN(@NotNull String SSN) {
		Collection<PersonalInfoModel> people = getTable().values();
		for (PersonalInfoModel person : people) {
			if (SSN.equals(person.getSSN()))
				return person;
		}
		return null;
	}
}
