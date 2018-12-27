package com.revature.project_0.repository.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.Test;

public class ModelUnitTest {
	@Test
	public void testPersonalInfoModelFidelity() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 0, 1);
		LocalDate dob = LocalDate.now();
		PersonalInfoModel personalInfoModel = PersonalInfoModel.getBuilder()
				.withFirstName("John")
				.withLastName("Doe")
				.withMiddleInitial('Z')
				.withDob(dob)
				.withCustomerId(1)
				.withEmail("john.doe@gmail.com")
				.withSSN("1111")
				.withPhoneNumber("1234567890")
				.withBeneficiary("Zoe Jo")
				.build();
		System.out.println("\n\nPersonal Info\n" + personalInfoModel);
	}
	
	@Test
	public void testAccountInfoModelAsClosedPrintsClosed() {
		AccountInfoModel accountInfoModel = AccountInfoModel.getBuilder()
				.withStatus(AccountStatus.CLOSED)
				.build();
		assertEquals("CLOSED", accountInfoModel.prettyPrintStatus());
	}
	
	@Test
	public void testAccountInfoModelAsOpenedPrintsOpened() {
		AccountInfoModel accountInfoModel = AccountInfoModel.getBuilder()
				.withStatus(AccountStatus.OPENED)
				.build();
		assertEquals("OPENED", accountInfoModel.prettyPrintStatus());
	}
	
	@Test
	public void testAccountInfoModelAsCheckingPrintsChecking() {
		AccountInfoModel accountInfoModel = AccountInfoModel.getBuilder()
				.withType(AccountType.CHECKING)
				.build();
		assertEquals("CHECKING", accountInfoModel.prettyPrintType());
	}
	
	@Test
	public void testAccountInfoModelAsSavingsPrintsSavings() {
		AccountInfoModel accountInfoModel = AccountInfoModel.getBuilder()
				.withType(AccountType.SAVINGS)
				.build();
		assertEquals("SAVINGS", accountInfoModel.prettyPrintType());
	}
	
	@Test
	public void accountInfoModelFidelity() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, 5, 1);
		LocalDate dateOpened = LocalDate.now();
		AccountInfoModel accountInfoModel = AccountInfoModel.getBuilder()
				.withAccountId(1)
				.withCustomerId(1l)
				.withJointCustomerId(29l)
				.withDateOpened(dateOpened)
				.withType(AccountType.CHECKING)
				.withStatus(AccountStatus.OPENED)
				.build();
		System.out.println("\n\nCreate Account\n" + accountInfoModel);
		
		accountInfoModel.setBalance(1000.00);
		System.out.println("\n\nMakeAccountJoint\n" + accountInfoModel);
		
		calendar.set(2017, 11, 31);
		LocalDate dateClosed = LocalDate.now();
		accountInfoModel.setStatus(AccountStatus.CLOSED);
		accountInfoModel.setDateClosed(dateClosed);
		System.out.println("\n\nCloseAccount\n" + accountInfoModel);
	}
}
