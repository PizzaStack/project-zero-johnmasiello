package com.revature.project_0.repository.model;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.project_0.App;

public class ApplicationModelTest {	
	private ApplicationModel applicationModel;
	@Before
	public void init() {
		applicationModel = ApplicationModel.getBuilder().build();
	}
	
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
	public void lesserApplicationIdComparesLess() {
		ApplicationModel m1 = ApplicationModel.getBuilder()
				.withApplicationId(0)
				.build();
		
		ApplicationModel m2 = ApplicationModel.getBuilder()
				.withApplicationId(1)
				.build();
		
		assertThat(0, greaterThan(m1.compareTo(m2)));
	}
	
	@Test
	public void greaterApplicationIdComparesGreater() {
		ApplicationModel m1 = ApplicationModel.getBuilder()
				.withApplicationId(1)
				.build();
		
		ApplicationModel m2 = ApplicationModel.getBuilder()
				.withApplicationId(0)
				.build();
		
		assertThat(0, lessThan(m1.compareTo(m2)));
	}
	
	@Test
	public void outOfBoxModelEqualsOutOfBoxModel() {
		assertTrue(applicationModel.equals(ApplicationModel.getBuilder().build()));
	}
	
	@Test 
	public void notEqualToNull() {
		assertFalse(applicationModel.equals(null));
	}
}
