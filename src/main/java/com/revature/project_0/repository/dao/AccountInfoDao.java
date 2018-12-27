package com.revature.project_0.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.ApplicationModel;

public class AccountInfoDao {
	@Nullable
	public AccountInfoModel approveApplication(@NotNull ApplicationModel application, 
			@Nullable String empId,
			@Nullable String adminId) {
		
		if (empId == null && adminId == null)
			return null;
		AccountInfoModel newAccount = null;
		String firstCreateAccount = "INSERT INTO account_info ("
				+ "account_name, customer_id, joint_customer_id, type, "
				+ "approved_one_id, approved_two_id) VALUES (?, ?, ?, ?, ?, ?);";
		String secondDeleteApplication = "DELETE FROM application WHERE id = ?;";
		String thirdQueryAccount = "SELECT * FROM account_info WHERE id = ?;";
		
		ConnectionHelper connectionHelper = ConnectionHelper.getinstance(); 
		PreparedStatement p1 = null;
		PreparedStatement p2 = null;
		PreparedStatement p3 = null;
		try (Connection connection = connectionHelper.getConnection()) {
			connection.setAutoCommit(false);
			p1 = connection.prepareStatement(firstCreateAccount, Statement.RETURN_GENERATED_KEYS);
			p1.setString(1, application.getAccountName());
			p1.setInt(2, (int) application.getCustomerId());
			p1.setInt(3, (int) application.getJointCustomerId());
			p1.setShort(4, (short) application.getType());
			p1.setString(5, empId != null ? empId : "");
			p1.setString(6, adminId != null ? adminId : "");
			if (p1.executeUpdate() <= 0) {
				connection.rollback();
				return null;
			}
			ResultSet rsKeys = p1.getGeneratedKeys();
			if (!rsKeys.next()) {
				connection.rollback();
				return null;
			}
			int accountKey = rsKeys.getInt("id");
			p2 = connection.prepareStatement(secondDeleteApplication);
			p2.setInt(1, (int) application.getApplicationId());
			if (p2.executeUpdate() <= 0) {
				connection.rollback();
				return null;
			}
			p3 = connection.prepareStatement(thirdQueryAccount);
			p3.setInt(1, accountKey);
			ResultSet rsAccount = p3.executeQuery();
			if (!rsAccount.next()) {
				connection.rollback();
				return null;
			}
			newAccount = loadAccount(rsAccount);
			
			connection.commit();
		} catch (SQLException e) {
			newAccount = null;
			 System.out.println(e.getMessage());
		} finally {
			connectionHelper.closeThing(p3);
			connectionHelper.closeThing(p2);
			connectionHelper.closeThing(p1);
		}
		return newAccount;
	}
	
	public AccountInfoModel queryAccountInfoById(int id) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM account_info WHERE id = ?")) {
			statement.setInt(1,  id);
			ResultSet rs = statement.executeQuery();
			rs.next();
			return loadAccount(rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	private AccountInfoModel loadAccount(ResultSet rsAccount) throws SQLException {
		LocalDate dateClosed = "infinity".equals(rsAccount.getString("date_closed")) ? null :
			rsAccount.getObject("date_closed", LocalDate.class);
		String empId = rsAccount.getString("approved_one_id").length() != 0 ? 
				rsAccount.getString("approved_one_id") : null;
		String adminId = rsAccount.getString("approved_two_id").length() != 0 ? 
				rsAccount.getString("approved_two_id") : null;
		return AccountInfoModel.getBuilder()
				.withAccountId(rsAccount.getInt("id"))
				.withAccountName(rsAccount.getString("account_name"))
				.withCustomerId((long) rsAccount.getInt("customer_id"))
				.withJointCustomerId((long) rsAccount.getInt("joint_customer_id"))
				.withStatus(rsAccount.getShort("status"))
				.withType(rsAccount.getShort("type"))
				.withDateOpened(rsAccount.getObject("date_opened", LocalDate.class))
				.withDateClosed(dateClosed)
				.withEmpId(empId)
				.withAdminId(adminId)
				.withBalance(rsAccount.getDouble("balance"))
				.build();
	}
}
