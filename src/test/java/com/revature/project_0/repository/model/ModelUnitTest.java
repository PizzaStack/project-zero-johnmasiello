package com.revature.project_0.repository.model;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class ModelUnitTest {
	@Test
	public void testPersonalInfoModelFidelity() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 0, 1);
		Date dob = calendar.getTime();
		
		PersonalInfoModel personalInfoModel = PersonalInfoModel.getBuilder()
				.withFirstName("John")
				.withLastName("Doe")
				.withMiddleInitial('Z')
				.withDob(dob)
				.withCustomerId(1)
				.withEmail("john.doe@gmail.com")
				.withLast4ssn("1111")
				.withPhoneNumber("1234567890")
				.withBeneficiary("Jane Doe")
				.build();
		System.out.println(personalInfoModel);
	}
}
