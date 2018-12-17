package com.revature.project_0.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.project_0.repository.model.PersonalInfoModel;

public class PersonalInfoTableTest {
	private PersonalInfoModel[] personalInfos;
	private PersonalInfoTable personalInfoTable;
	
	@Before
	public void init() {
		personalInfos = new PersonalInfoModel[] {
				PersonalInfoModel.getBuilder()
					.withCustomerId(0l)
					.withFirstName("John")
					.withLastName("Masiello")
					.build(),
				PersonalInfoModel.getBuilder()
					.withCustomerId(1l)
					.withFirstName("Johnny")
					.withLastName("AppleSeed")
					.build(),
		};
		personalInfoTable = new PersonalInfoTable();
	}
	
	@Test
	public void addRecord() {
		assertTrue(personalInfoTable.addRecord(0l, personalInfos[0]));
	}
	
	@Test
	public void verifyFirstNameOfAddedRecord() {
		personalInfoTable.addRecord(0l, personalInfos[0]);
		assertEquals("John", personalInfoTable.selectRecord(0l).getFirstName());
	}
	
	@Test
	public void deleteRecordPasses() {
		personalInfoTable.addRecord(1l, personalInfos[1]);
		assertTrue(personalInfoTable.deleteRecord(1l));
	}
}
