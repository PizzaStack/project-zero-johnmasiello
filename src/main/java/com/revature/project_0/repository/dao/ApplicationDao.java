package com.revature.project_0.repository.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.sql.Types;

import org.jetbrains.annotations.Nullable;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.model.ApplicationModel;
import com.revature.project_0.repository.model.CustomerLoginModel;

public class ApplicationDao {
	public ApplicationModel insertNewApplication(ApplicationModel application) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (CallableStatement statement = connection.prepareCall(
				"INSERT INTO application (account_name, customer_id, joint_customer_id, "
				+ "joint_customer_ssn, type) VALUES (?, ?, ?, ?, ?)")) {
			statement.setString(1, application.getAccountName());
			statement.setInt(2, (int) application.getCustomerId());
			statement.setInt(3, (int) application.getJointCustomerId());
			statement.setString(4, application.getJointCustomerSSN());
			statement.setShort(5, (short) application.getType());
			statement.execute();
			if (statement.getUpdateCount() <= 0)
				return null;
			return application;
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			ConnectionHelper.getinstance().closeConnection();
		}
		return null;
	}
}