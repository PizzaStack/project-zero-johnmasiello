package com.revature.project_0.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.model.AccountInfoModel;
import com.revature.project_0.repository.model.ApplicationModel;

public class AccountInfoDao {
	@Nullable
	public AccountInfoModel approveApplication(@NotNull ApplicationModel application, 
			@NotNull String approverId) {
		
		if (approverId == null)
			return null;
		AccountInfoModel newAccount = null;
		String firstCreateAccount = "INSERT INTO account_info ("
				+ "account_name, customer_id, joint_customer_id, type, "
				+ "approved_app_id) VALUES (?, ?, ?, ?, ?);";
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
			p1.setString(5, approverId);
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
	
	public boolean updateOnAccountApproved(int accountId, int status, String approveId) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"UPDATE account_info SET status = ?, approved_acct_id = ? WHERE account_info.id = ?")) {
			statement.setShort(1, (short) status);
			statement.setString(2, approveId);
			statement.setInt(3, accountId);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return false;
	}
	
	public boolean updateBalance(int accountId, double newBalance) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"UPDATE account_info SET balance = ? WHERE account_info.id = ?")) {
			statement.setDouble(1, newBalance);
			statement.setInt(2, accountId);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return false;
	}
	
	public boolean updateMultipleBalancesInSingleTransaction(int accountId_1, double newBalance_1,
			int accountId_2, double newBalance_2) {
		boolean result = false;
		ConnectionHelper connectionHelper = ConnectionHelper.getinstance(); 
		PreparedStatement p1 = null;
		PreparedStatement p2 = null;
		final String SQL_UPDATE = "UPDATE account_info SET balance = ? WHERE id = ?;"; 
		
		try (Connection connection = connectionHelper.getConnection()) {
			connection.setAutoCommit(false);
			p1 = connection.prepareStatement(SQL_UPDATE);
			p1.setDouble(1, newBalance_1);
			p1.setInt(2, accountId_1);
			if (p1.executeUpdate() > 0) {
				p2 = connection.prepareStatement(SQL_UPDATE);
				p2.setDouble(1, newBalance_2);
				p2.setInt(2, accountId_2);
				if (p2.executeUpdate() > 0) {
					result = true;
				} else {
					connection.rollback();
				}
			} else {
				connection.rollback();
			}
			connection.commit();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			connectionHelper.closeThing(p2);
			connectionHelper.closeThing(p1);
		}
		return result;
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
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return null;
	}
	
	public List<AccountInfoModel> queryAccountInfoByCustomerId(int customer_id) {
		List<AccountInfoModel> accounts = new ArrayList<>();
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM account_info WHERE customer_id = ?")) {
			statement.setInt(1,  customer_id);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
				accounts.add(loadAccount(rs));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return accounts;
	}
	
	public Collection<AccountInfoModel> queryAllAccountsForAllCustomers() {
		List<AccountInfoModel> accounts = new ArrayList<>();
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM account_info");
			while (rs.next())
				accounts.add(loadAccount(rs));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return accounts;
	}
	
	public boolean deleteAccountInfo(int id) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"DELETE FROM account_info WHERE id = ?")) {
			statement.setInt(1,  id);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return false;
	}
	
	private AccountInfoModel loadAccount(ResultSet rsAccount) throws SQLException {
		LocalDate dateClosed = "infinity".equals(rsAccount.getString("date_closed")) ? null :
			rsAccount.getObject("date_closed", LocalDate.class);
		return AccountInfoModel.getBuilder()
				.withAccountId(rsAccount.getInt("id"))
				.withAccountName(rsAccount.getString("account_name"))
				.withCustomerId((long) rsAccount.getInt("customer_id"))
				.withJointCustomerId((long) rsAccount.getInt("joint_customer_id"))
				.withStatus(rsAccount.getShort("status"))
				.withType(rsAccount.getShort("type"))
				.withDateOpened(rsAccount.getObject("date_opened", LocalDate.class))
				.withDateClosed(dateClosed)
				.withAppAproverId(internalizeEmployeeId(rsAccount.getString("approved_app_id")))
				.withAcctApproverId(internalizeEmployeeId(rsAccount.getString("approved_acct_id")))
				.withBalance(rsAccount.getDouble("balance"))
				.build();
	}
	
	@Nullable
	private String internalizeEmployeeId(String employeeId_db) {
		return employeeId_db.length() != 0 ? employeeId_db : null;
	}
}
