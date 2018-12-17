package com.revature.project_0.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.project_0.repository.model.*;

public class ApplicationTableTest {
	private ApplicationModel[] applicationInfos;
	private ApplicationTable applicationTable;
	
	@Before
	public void init() {
		applicationInfos = new ApplicationModel[] {
			ApplicationModel.getBuilder()
				.withApplicationId(0l)
				.withCustomerId(9l)
				.build(),
			ApplicationModel.getBuilder()
				.withApplicationId(1l)
				.withCustomerId(10l)
				.build()
		};
		applicationTable = new ApplicationTable();
	}
	
	@Test
	public void addRecords() {
		for (ApplicationModel a : applicationInfos)
			assertTrue(applicationTable.addRecord(a.getApplicationId(), a));
	}
	
	@Test 
	public void deleteARecord() {
		applicationTable.addRecord(0, applicationInfos[0]);
		assertTrue(applicationTable.deleteRecord(0));
	}
	
	@Test 
	public void deleteARecordFailsForWrongApplicationId() {
		assertFalse(applicationTable.deleteRecord(0));
	}
	
	@Test
	public void getAssociatedRecordsForCustomerPasses() {
		applicationTable.addRecord(1, applicationInfos[1]);
		assertEquals(1l, applicationTable.getAllAssociatedApplications(10l).get(0).getApplicationId());
		
		System.out.println("Application id: " +
				applicationTable.getAllAssociatedApplications(10l).get(0).prettyPrintApplicationId());
	}
}
