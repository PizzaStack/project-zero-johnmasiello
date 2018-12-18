package com.revature.project_0.entity.actions;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.repository.model.ApplicationModel;
import static com.revature.project_0.repository.TableOutcome.*;

public class EmployeeManageApplication implements ManageApplication{
	private Repository repository;
	private int errorCode;

	public EmployeeManageApplication(Repository repository) {
		this.repository = repository;
	}
	
	public boolean approveApplication(long appId, String empId) {
		errorCode = OK;
		ApplicationModel applicationModel = repository.getApplicationModel(appId);
		if (applicationModel == null) {
			errorCode = NO_SUCH_RECORD;
			return false;
		} else if (repository.approveApplication(applicationModel, empId) == null) {
			errorCode = FAIL_TO_UPDATE;
			return false;
		} return true;
	}
	
	public boolean denyApplication(long appId, String empId) {
		errorCode = OK;
		ApplicationModel applicationModel = repository.getApplicationModel(appId);
		if (applicationModel == null) {
			errorCode = NO_SUCH_RECORD;
			return false;
		} else if (repository.rejectApplication(applicationModel, empId) == null) {
			errorCode = FAIL_TO_UPDATE;
			return false;
		} return true;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
