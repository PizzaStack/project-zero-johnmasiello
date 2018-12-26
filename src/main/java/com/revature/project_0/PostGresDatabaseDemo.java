package com.revature.project_0;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.dao.CustomerLoginDao;
import com.revature.project_0.repository.model.CustomerLoginModel;

public class PostGresDatabaseDemo {
	private static ConnectionHelper connectionHelper = ConnectionHelper.getinstance();
	
	public static void main(String[] args) {
		try {
			System.out.println("AUTO-GENERATED CUSTOMER_ID: "+new CustomerLoginDao().push(CustomerLoginModel.getBuilder()
					.withUsername("John++")
					.withPassword("SWORDFISH")
					.build()));
//			new CustomerLoginDao().createTableTest();
		} finally {
			connectionHelper.closeConnection();
		}
	}
}
