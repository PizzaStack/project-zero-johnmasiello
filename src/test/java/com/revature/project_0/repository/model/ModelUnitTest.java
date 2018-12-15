package com.revature.project_0.repository.model;

import static org.junit.Assert.*;

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
				.withBeneficiary("Zoe Jo")
				.build();
		System.out.println("\n\nPersonal Info\n" + personalInfoModel);
	}
	
	@Test
	public void testAccountInfoModelAsClosedPrintsClosed() {
		AccountInfoModel accountInfoModel = AccountInfoModel.getBuilder()
				.withStatus(AccountInfoModel.AccountStatus.CLOSED)
				.build();
		assertEquals("CLOSED", accountInfoModel.prettyPrintStatus());
	}
	
	@Test
	public void testAccountInfoModelAsOpenedPrintsOpened() {
		AccountInfoModel accountInfoModel = AccountInfoModel.getBuilder()
				.withStatus(AccountInfoModel.AccountStatus.OPENED)
				.build();
		assertEquals("OPENED", accountInfoModel.prettyPrintStatus());
	}
	
	@Test
	public void testAccountInfoModelAsCheckingPrintsChecking() {
		AccountInfoModel accountInfoModel = AccountInfoModel.getBuilder()
				.withType(AccountInfoModel.AccountType.CHECKING)
				.build();
		assertEquals("CHECKING", accountInfoModel.prettyPrintType());
	}
	
	@Test
	public void testAccountInfoModelAsSavingsPrintsSavings() {
		AccountInfoModel accountInfoModel = AccountInfoModel.getBuilder()
				.withType(AccountInfoModel.AccountType.SAVINGS)
				.build();
		assertEquals("SAVINGS", accountInfoModel.prettyPrintType());
	}
	
	@Test
	public void accountInfoModelFidelity() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, 5, 1);
		Date dateOpened = calendar.getTime();
		AccountInfoModel accountInfoModel = AccountInfoModel.getBuilder()
				.withAccountId(1)
				.withCustomerId(1l)
				.withDateOpened(dateOpened)
				.withType(AccountInfoModel.AccountType.CHECKING)
				.withStatus(AccountInfoModel.AccountStatus.OPENED)
				.build();
		System.out.println("\n\nCreate Account\n" + accountInfoModel);
		
		accountInfoModel.setJointCustomerId(29);
		accountInfoModel.setBalance(1000.00);
		System.out.println("\n\nMakeAccountJoint\n" + accountInfoModel);
		
		calendar.set(2017, 11, 31);
		Date dateClosed = calendar.getTime();
		accountInfoModel.setStatus(AccountInfoModel.AccountStatus.CLOSED);
		accountInfoModel.setDateClosed(dateClosed);
		System.out.println("\n\nCloseAccount\n" + accountInfoModel);
	}
}
