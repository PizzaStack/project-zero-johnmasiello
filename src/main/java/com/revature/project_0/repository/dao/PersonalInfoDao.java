package com.revature.project_0.repository.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import com.revature.project_0.connection.ConnectionHelper;
import com.revature.project_0.repository.model.PersonalInfoModel;

public class PersonalInfoDao {
	public PersonalInfoModel upsert(PersonalInfoModel personalInfo) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (CallableStatement statement = connection.prepareCall(new StringBuilder()
				.append("INSERT INTO personal_info (customer_id, first_name, last_name, middle_initial, ")
				.append("dob, ssn, email, phone_number, beneficiary) ")
				.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)")
				.append("ON CONFLICT ON CONSTRAINT personal_info_pkey ")
				.append("DO UPDATE SET first_name = ?, last_name = ?, middle_initial = ?, dob = ?, ")
				.append("ssn = ?, email = ?, phone_number = ?, beneficiary = ? ")
				.append("WHERE personal_info.customer_id = ?")
				.toString())) {
			statement.setInt(1, (int) personalInfo.getCustomerId());
			statement.setString(2, personalInfo.getFirstName());
			statement.setString(3, personalInfo.getLastName());
			statement.setString(4, Character.toString(personalInfo.getMiddleInitial()));
			statement.setObject(5, personalInfo.getDob());
			statement.setString(6, personalInfo.getSSN());
			statement.setString(7, personalInfo.getEmail());
			statement.setString(8, personalInfo.getPhoneNumber());
			statement.setString(9, personalInfo.getBeneficiary());
			statement.setString(10, personalInfo.getFirstName());
			statement.setString(11, personalInfo.getLastName());
			statement.setString(12, Character.toString(personalInfo.getMiddleInitial()));
			statement.setObject(13, personalInfo.getDob());
			statement.setString(14, personalInfo.getSSN());
			statement.setString(15, personalInfo.getEmail());
			statement.setString(16, personalInfo.getPhoneNumber());
			statement.setString(17, personalInfo.getBeneficiary());
			statement.setInt(18, (int) personalInfo.getCustomerId());
			statement.execute();
			if (statement.getUpdateCount() <= 0)
				return null;
			return personalInfo;
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return null;
	}
	
	public PersonalInfoModel queryPersonalInfoByCustomerId(int id) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM personal_info WHERE customer_id = ?")) {
			statement.setInt(1, id);
			statement.execute();
			ResultSet rs = statement.getResultSet();
			if (rs.next())
				return loadPersonalInfo(rs);
			return null;
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return null;
	}
	
	public PersonalInfoModel queryPersonalInfoBySSN(String ssn) {
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM personal_info WHERE ssn = ?")) {
			statement.setString(1, ssn);
			statement.execute();
			ResultSet rs = statement.getResultSet();
			if (rs.next())
				return loadPersonalInfo(rs);
			return null;
		} catch (SQLException e){
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return null;
	}

	public Collection<PersonalInfoModel> queryPersonalInfoForAllCustomers() {
		List<PersonalInfoModel> allInfos = new ArrayList<>();
		Connection connection = ConnectionHelper.getinstance().getConnection();
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(
					"Select * FROM personal_info");
			while (rs.next()) {
				allInfos.add(loadPersonalInfo(rs));
			}
		} catch (SQLException e){
			System.out.println(e.getMessage());
			allInfos.clear();
		} finally {
			ConnectionHelper.getinstance().closeConnection();
		}
		return allInfos;
	}
	
	private PersonalInfoModel loadPersonalInfo(ResultSet rs) throws SQLException {
		return PersonalInfoModel.getBuilder()
				.withCustomerId(rs.getInt("customer_id"))
				.withFirstName(rs.getString("first_name"))
				.withLastName(rs.getString("last_name"))
				.withMiddleInitial(rs.getString("middle_initial").charAt(0))
				.withDob(rs.getObject("dob", LocalDate.class))
				.withSSN(rs.getString("ssn"))
				.withPhoneNumber(rs.getString("phone_number"))
				.withEmail(rs.getString("email"))
				.withBeneficiary(rs.getString("beneficiary"))
				.build();		
	}
}
