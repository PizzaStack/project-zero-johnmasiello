package com.revature.project_0.repository.model;

import java.util.Date;

import org.jetbrains.annotations.NotNull;

import com.revature.project_0.util.Util;

public class PersonalInfoModel implements Comparable<PersonalInfoModel> {
	private final long customerId;
	private String firstName;
	private String lastName;
	private char middleInitial;
	private Date dob;
	private String ssn;
	private String email;
	private String phoneNumber;
	private String beneficiary;
	
	public static final long NO_ID = -1;
	
	private static final Builder builder = new Builder();
	
	public static final class Builder {
		private long customerId;
		private String firstName;
		private String lastName;
		private char middleInitial;
		private Date dob;
		private String ssn;
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
			ssn = "";
			email = "";
			phoneNumber = "";
			beneficiary = "";
		}

		public Builder withCustomerId(long customerId) {
			this.customerId = customerId;
			return this;
		}

		public Builder withFirstName(@NotNull String firstName) {
			if (firstName != null)
				this.firstName = firstName;
			return this;
		}

		public Builder withLastName(@NotNull String lastName) {
			if (lastName != null)
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

		public Builder withSSN(@NotNull String ssn) {
			if (ssn != null)
				this.ssn = ssn;
			return this;
		}

		public Builder withEmail(@NotNull String email) {
			if (email != null)
				this.email = email;
			return this;
		}

		public Builder withPhoneNumber(@NotNull String phoneNumber) {
			if (phoneNumber != null)
				this.phoneNumber = phoneNumber;
			return this;
		}

		public Builder withBeneficiary(@NotNull String beneficiary) {
			if (beneficiary != null)
				this.beneficiary = beneficiary;
			return this;
		}
		
		public PersonalInfoModel build() {
			return new PersonalInfoModel(customerId, firstName, lastName, middleInitial, dob, ssn, email, phoneNumber, beneficiary);
		}
	}
	
	private PersonalInfoModel(long customerId, String firstName, String lastName, char middleInitial, Date dob,
			String ssn, String email, String phoneNumber, String beneficiary) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.dob = dob;
		this.ssn = ssn;
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

	public String getFirstName() {
		return firstName;
	}

	public boolean setFirstName(@NotNull String firstName) {
		if (firstName != null && firstName.length() > 1) {
			this.firstName = firstName;
			return true;
		}
		return false;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean setLastName(@NotNull String lastName) {
		if (lastName != null && lastName.length() > 1) {
			this.lastName = lastName;
			return true;
		} return false;
	}

	public char getMiddleInitial() {
		return middleInitial;
	}

	public boolean setMiddleInitial(char middleInitial) {
		this.middleInitial = middleInitial;
		return true;
	}

	public Date getDob() {
		return dob;
	}

	public boolean setDob(@NotNull Date dob) {
		if (dob != null) {
			this.dob = dob;
			return true;
		} return false;
	}

	public String getSSN() {
		return ssn;
	}

	public boolean setSSN(@NotNull String ssn) {
		if (ssn == null)
			return false;
		String trimmedLastSsn = trimToJustDigits(ssn);
		if (trimmedLastSsn.length() != 9)
			return false;
		this.ssn = trimmedLastSsn;
		return true;
	}
	
	public String prettyPrintLast4SSN() {
		if (ssn == null || ssn.length() != 9)
			return Util.NOT_AVAILABLE;
		return "XXX-XX-" + ssn.substring(5);
	}

	public String getEmail() {
		return email;
	}

	public boolean setEmail(@NotNull String email) {
		if (email == null || !email.contains("@"))
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
		if (phoneNumber == null)
			return false;
		String trimmedPhoneNumber = trimToJustDigits(phoneNumber);
		if (trimmedPhoneNumber.length() != 10)
			return false;
		this.phoneNumber = trimmedPhoneNumber;
		return true;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public boolean setBeneficiary(@NotNull String beneficiary) {
		if (beneficiary != null && beneficiary.length() > 1) {
			this.beneficiary = beneficiary;
			return true;
		} return false;
	}
	
	private String trimToJustDigits(String raw) {
		return raw.replaceAll("[^0-9]+", "");
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("Customer Id: ")
				.append(Util.zeroPadCondensedId(customerId))
				.append("\nLast name: ")
				.append(lastName)
				.append("\nFirst name (MI): ")
				.append(firstName)
				.append(' ')
				.append(middleInitial)
				.append("\nDOB: ")
				.append(dob == null ? "" : dob)
				.append("\nSSN: ")
				.append(prettyPrintLast4SSN())
				.append("\nEmail: ")
				.append(email)
				.append("\nPhn (xxx)xxx-xxxx: ")
				.append(prettyPrintPhoneNumber())
				.append("\nBeneficiary: ")
				.append(beneficiary)
				.toString();
	}

	@Override
	public int compareTo(PersonalInfoModel m) {
		return this.customerId < m.customerId ? -1 :
			this.customerId == m.customerId ? 0 :
				1;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof PersonalInfoModel && compareTo((PersonalInfoModel)obj) == 0;
	}
}
