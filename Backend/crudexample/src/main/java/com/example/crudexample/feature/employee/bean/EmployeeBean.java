package com.example.crudexample.feature.employee.bean;

import com.example.crudexample.api.message.response.AbstractResponse;
import com.example.crudexample.dto.EmployeeDTO;

public interface EmployeeBean {
	
	/**
	 * 
	 * @param accessorId
	 * @param sessionToken
	 * @return AbstractResponse - List of all Users
	 */
	AbstractResponse processGetAllUserProfile(String accessorId, String sessionToken);
	
	/**
	 * 
	 * @param id
	 * @return AbstractResponse - Delete User Success
	 */
	AbstractResponse processDelete(long id);

	AbstractResponse processAddUser(EmployeeDTO employeedto);
	
	
}
