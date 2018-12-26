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
import com.revature.project_0.repository.model.CustomerLoginModel;

public class CustomerLoginDao {
	@Nullable
	public CustomerLoginModel insertNewSignUp(String username, String password) {
		final int customerColumnIdColumn = 3;
		
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (Statement statement = connection.createStatement()) {
			String updateStatement = String.format("INSERT INTO public.customer_login(username, password)" + 
					" VALUES ('%s', '%s');", 
					username,
					password);
			if (statement.executeUpdate(updateStatement,
				Statement.RETURN_GENERATED_KEYS) <= 0)
				return null;
			ResultSet rsKeys = statement.getGeneratedKeys();
			while (rsKeys.next()) {
				return CustomerLoginModel.getBuilder()
						.withUsername(username)
						.withPassword(password)
						.withCustomerId(rsKeys.getInt(customerColumnIdColumn))
						.build();
			}
		} catch (SQLFeatureNotSupportedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			ConnectionHelper.getinstance().closeConnection();
		}
		return null;
	}
	
	@Nullable
	public CustomerLoginModel queryLogin(String username, String password) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (CallableStatement statement = connection.prepareCall(
				"{ ? = call authenticate_customer_login( ?, ? )}")) {
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, username);
			statement.setString(3, password);
			statement.execute();
			int result = statement.getInt(1);
			if (result == -1)
				return null;
			return CustomerLoginModel.getBuilder()
					.withUsername(username)
					.withPassword(password)
					.withCustomerId(result)
					.build();
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			ConnectionHelper.getinstance().closeConnection();
		}
		return null;
	}
	
	public boolean queryIsUsernameUnique(String requestedUsername) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT COUNT(username) FROM customer_login WHERE username = ?")) {
			statement.setString(1, requestedUsername);
			statement.execute();
			ResultSet rs = statement.getResultSet();
			return rs.next() && rs.getInt(1) == 0;
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			ConnectionHelper.getinstance().closeConnection();
		}
		return false;
	}
}
