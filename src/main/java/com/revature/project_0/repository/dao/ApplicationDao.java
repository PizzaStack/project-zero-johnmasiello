package com.revature.project_0.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.model.ApplicationModel;

public class ApplicationDao {
	public ApplicationModel insertNewApplication(ApplicationModel application) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
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
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return null;
	}
	
	public boolean deleteApplication(int id) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"DELETE FROM application WHERE id = ?")) {
			statement.setInt(1,  id);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return false;
	}
	
	public ApplicationModel queryApplicationById(int id) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM application WHERE id = ?")) {
			statement.setInt(1, id);
			statement.execute();
			ResultSet rs = statement.getResultSet();
			if (rs.next())
				return loadApplication(rs);
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return null;
	}
	
	public List<ApplicationModel> queryApplicationsByCustomerId(int id) {
		List<ApplicationModel> list = new ArrayList<>();
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM application WHERE customer_id = ? ORDER BY id")) {
			statement.setInt(1, id);
			statement.execute();
			ResultSet rs = statement.getResultSet();
			while (rs.next())
				list.add(loadApplication(rs));
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			list.clear();
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return list;
	}

	public Collection<ApplicationModel> queryApplicationsForAllCustomers() {
		List<ApplicationModel> allInfos = new ArrayList<>();
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(
					"Select * FROM application ORDER BY id");
			while (rs.next()) {
				allInfos.add(loadApplication(rs));
			}
		} catch (SQLException e){
			System.out.println(e.getMessage());
			allInfos.clear();
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return allInfos;
	}
	
	private ApplicationModel loadApplication(ResultSet rs) throws SQLException {
		return ApplicationModel.getBuilder()
				.withApplicationId(rs.getInt("id"))
				.withAccountName(rs.getString("account_name"))
				.withCustomerId(rs.getInt("customer_id"))
				.withJointCustomerId(rs.getInt("joint_customer_id"))
				.withJointCustomerSSN(rs.getString("joint_customer_ssn"))
				.withType((int) rs.getShort("type"))
				.build();
	}
}