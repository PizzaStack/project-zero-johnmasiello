package com.revature.project_0.repository.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.model.CustomerLoginModel;

public class CustomerLoginDao {
	public int push(CustomerLoginModel customerlogin) {
		final int fail = -1;
		final int customerColumnIdColumn = 3;
		
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (Statement statement = connection.createStatement()) {
			String updateStatement = String.format("INSERT INTO public.customer_login(username, password)" + 
					" VALUES ('%s', '%s');", 
					customerlogin.getUsername(),
					customerlogin.getPassword());
			System.out.println("Update Statement: \n" + updateStatement);
			if (statement.executeUpdate(updateStatement,
				Statement.RETURN_GENERATED_KEYS) <= 0)
			return fail;
			ResultSet rsKeys = statement.getGeneratedKeys();
			while (rsKeys.next()) {
				return rsKeys.getInt(customerColumnIdColumn);
			}
		} catch (SQLFeatureNotSupportedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			ConnectionHelper.getinstance().closeConnection();
		}
		return fail;
	}
	
	public void createTableTest() {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		String qs = "CREATE TABLE IF NOT EXISTS user2(user_id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE,password varchar(225),islogged varchar(10))";
		
		try (Statement statement = connection.createStatement()) {
			System.out.println(qs);
			statement.executeQuery(qs);
		} catch (SQLFeatureNotSupportedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			ConnectionHelper.getinstance().closeConnection();
		}
	}
}
