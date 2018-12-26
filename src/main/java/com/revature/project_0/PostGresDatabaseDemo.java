package com.revature.project_0;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.dao.CustomerLoginDao;
import com.revature.project_0.repository.model.CustomerLoginModel;

public class PostGresDatabaseDemo {
	private static ConnectionHelper connectionHelper = ConnectionHelper.getinstance();
	
	public static void main(String[] args) {
		try {
			CustomerLoginModel  customerLoginModel;
//			customerLoginModel = new CustomerLoginDao().pushNewSignUp("John++", "SWORDFISH");
//			customerLoginModel = new CustomerLoginDao().queryLogin("John++", "SWORDFISH");
//			if (customerLoginModel != null)
//				System.out.println("AUTO-GENERATED CUSTOMER_ID: "+customerLoginModel.getCustomerId());
//			else
//				System.out.println("AUTO-GENERATED CUSTOMER_ID: "+-1);
			
			System.out.println(Boolean.toString(new CustomerLoginDao().queryIsUsernameUnique("MERRLL")));
		} finally {
			connectionHelper.closeConnection();
		}
	}
}
