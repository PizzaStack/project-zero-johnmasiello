package com.revature.project_0.repository.model;

import java.util.Comparator;

public class CustomerLoginModelUsernameComparator implements Comparator<CustomerLoginModel> {

	@Override
	public int compare(CustomerLoginModel o1, CustomerLoginModel o2) {
		return o1.getUsername().compareTo(o2.getUsername());
	}
}
