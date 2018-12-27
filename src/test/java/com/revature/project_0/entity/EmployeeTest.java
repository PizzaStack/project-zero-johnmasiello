package com.revature.project_0.entity;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.dao.AccountInfoDao;
import com.revature.project_0.repository.dao.ApplicationDao;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.ApplicationModel;

public class EmployeeTest {
//	@Test
	public void testApplicationApprovalAndSubsequentAccountCreation() {
		List<ApplicationModel> applications = new ApplicationDao().queryApplicationsByCustomerId(4);
		if (applications.size() > 0) {
			AccountInfoModel account = new AccountInfoDao().approveApplication(applications.get(0), 
					"Employee", 
					null);
			assertNotNull(account);
			System.out.println(account);
		}
	}
	@Test
	public void testQueryAccountById() {
		System.out.println(new AccountInfoDao().queryAccountInfoById(3));
	}
	@After
	public void finish() {
		ConnectionHelper.getinstance().closeConnection();
	}
}