package com.revature.project_0.repository.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ApplicationModelTest {	
	@Test
	public void buildApplicationModel() {
		ApplicationModel applicationModel = ApplicationModel.getBuilder()
				.withApplicationId(0)
				.withCustomerId(0)
				.build();
	}
	
	@Test
	public void buildApplicationModelWithApplicationId() {
		ApplicationModel applicationModel = ApplicationModel.getBuilder()
				.withApplicationId(0)
				.build();
		assertEquals(0, applicationModel.getApplicationId());
	}
	
	@Test
	public void buildApplicationModelWithCustomerId() {
		ApplicationModel applicationModel = ApplicationModel.getBuilder()
				.withCustomerId(0)
				.build();
		assertEquals(0, applicationModel.getCustomerId());
	}
	
	@Test
	public void buildApplicationModelWithJointCustomerId() {
		ApplicationModel applicationModel = ApplicationModel.getBuilder()
				.withJointCustomerId(0)
				.build();
		assertEquals(0, applicationModel.getJointCustomerId());
	}
	
	@Test
	public void setApplicationId() {
		ApplicationModel applicationModel = ApplicationModel.getBuilder().build();
		applicationModel.setApplicationId(99);
		assertEquals(99, applicationModel.getApplicationId());
	}
	
	@Test
	public void lesserApplicationIdComparesLess() {
		ApplicationModel m1 = ApplicationModel.getBuilder()
				.withApplicationId(0)
				.build();
		
		ApplicationModel m2 = ApplicationModel.getBuilder()
				.withApplicationId(1)
				.build();
		
		assertTrue(0 > m1.compareTo(m2));
	}
	
	@Test
	public void greaterApplicationIdComparesGreater() {
		ApplicationModel m1 = ApplicationModel.getBuilder()
				.withApplicationId(1)
				.build();
		
		ApplicationModel m2 = ApplicationModel.getBuilder()
				.withApplicationId(0)
				.build();
		
		assertTrue(0 < m1.compareTo(m2));
	}
}
