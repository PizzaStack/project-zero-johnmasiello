package com.revature.project_0.io;

import com.revature.project_0.entity.Validating;

public interface DataInputting {
	public boolean acceptStringAsTokenWithAttempts(String fieldLabel, 
			String showCondition, int numberOfAttempts, Validating validator);
}
