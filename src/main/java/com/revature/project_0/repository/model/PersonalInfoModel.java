package com.revature.project_0.repository.model;

import java.util.Date;
import java.util.zip.ZipError;

import org.jetbrains.annotations.NotNull;

import com.revature.project_0.util.Util;

public class PersonalInfoModel implements Comparable<PersonalInfoModel> {
	private long customerId;
	private String firstName;
	private String lastName;
	private char middleInitial;
	private Date dob;
	private String last4ssn;
	private String email;
	private String phoneNumber;
	private String beneficiary;
	
	public static final int NO_ID = -1;
	
	private static final Builder builder = new Builder();
	
	public static final class Builder {
		private long customerId;
		private String firstName;
		private String lastName;
		private char middleInitial;
		private Date dob;
		private String last4ssn;
		private String email;
		private String phoneNumber;
		private String beneficiary;
		
		{
			reset();
		}
		
		private void reset() {
			customerId = NO_ID;
			firstName = lastName = "";
			middleInitial = ' ';
			dob = null;
			last4ssn = "";
			email = "";
			phoneNumber = "";
			beneficiary = "";
		}
		
		private Builder() { }

		public Builder withCustomerId(long customerId) {
			this.customerId = customerId;
			return this;
		}

		public Builder withFirstName(@NotNull String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder withLastName(@NotNull String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder withMiddleInitial(char middleInitial) {
			this.middleInitial = middleInitial;
			return this;
		}

		public Builder withDob(Date dob) {
			this.dob = dob;
			return this;
		}

		public Builder withLast4ssn(@NotNull String last4ssn) {
			this.last4ssn = last4ssn;
			return this;
		}

		public Builder withEmail(@NotNull String email) {
			this.email = email;
			return this;
		}

		public Builder withPhoneNumber(@NotNull String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public Builder withBeneficiary(@NotNull String beneficiary) {
			this.beneficiary = beneficiary;
			return this;
		}
		
		public PersonalInfoModel build() {
			return new PersonalInfoModel(customerId, firstName, lastName, middleInitial, dob, last4ssn, email, phoneNumber, beneficiary);
		}
	}
	
	private PersonalInfoModel(long customerId, String firstName, String lastName, char middleInitial, Date dob,
			String last4ssn, String email, String phoneNumber, String beneficiary) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.dob = dob;
		this.last4ssn = last4ssn;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.beneficiary = beneficiary;
	}
	
	public static Builder getBuilder() {
		builder.reset();
		return builder;
	}

	public long getCustomerId() {
		return customerId;
	}

	public boolean setCustomerId(long customerId) {
		if (customerId < 0)
			return false;
		this.customerId = customerId;
		return true;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(@NotNull String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(@NotNull String lastName) {
		this.lastName = lastName;
	}

	public char getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(char middleInitial) {
		this.middleInitial = middleInitial;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(@NotNull Date dob) {
		this.dob = dob;
	}

	public String getLast4ssn() {
		return last4ssn;
	}

	public boolean setLast4ssn(@NotNull String last4ssn) {
		String trimmedLastSsn = trimToJustDigits(last4ssn);
		if (trimmedLastSsn.length() < 4)
			return false;
		this.last4ssn = trimmedLastSsn.substring(trimmedLastSsn.length() - 4);
		return true;
	}

	public String getEmail() {
		return email;
	}

	public boolean setEmail(@NotNull String email) {
		if (!email.contains("@") || !email.contains("."))
				return false;
		this.email = email;
		return true;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String prettyPrintPhoneNumber() {
		if (phoneNumber.length() < 10)
			return phoneNumber;
		return new StringBuilder("(")
				.append(phoneNumber, 0, 3)
				.append(')')
				.append(phoneNumber, 3, 6)
				.append('-')
				.append(phoneNumber, 6, 10)
				.toString();
	}

	public boolean setPhoneNumber(@NotNull String phoneNumber) {
		String trimmedPhoneNumber = trimToJustDigits(phoneNumber);
		if (trimmedPhoneNumber.length() != 10)
			return false;
		this.phoneNumber = trimmedPhoneNumber;
		return true;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(@NotNull String beneficiary) {
		this.beneficiary = beneficiary;
	}
	
	private String trimToJustDigits(String raw) {
		return raw.replaceAll("[^0-9]+", "");
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("Customer Id: ")
				.append(Util.zeroPadId(customerId))
				.append("\nLast name: ")
				.append(lastName)
				.append("\nFirst name (MI): ")
				.append(firstName)
				.append(' ')
				.append(middleInitial)
				.append("\nDOB: ")
				.append(dob == null ? "" : dob)
				.append("\nLast Four SSN: ")
				.append(last4ssn)
				.append("\nEmail: ")
				.append(email)
				.append("\nPhn (xxx)xxx-xxxx: ")
				.append(prettyPrintPhoneNumber())
				.append("\nBeneficiary: ")
				.append(beneficiary)
				.toString();
	}

	@Override
	public int compareTo(PersonalInfoModel p) {
		return this.customerId < p.customerId ? -1 :
			this.customerId == p.customerId ? 0 :
				1;
	}
}
