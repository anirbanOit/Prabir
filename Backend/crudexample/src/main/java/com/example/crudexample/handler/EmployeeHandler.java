package com.example.crudexample.handler;

import java.util.List;

import com.example.crudexample.dto.EmployeeDTO;

public interface EmployeeHandler {
	
	/**
	 * 
	 * @param errorCodePrefix
	 * @return EmployeeDTO
	 */
	List<EmployeeDTO> performGetUsersProfileDetail(String errorCodePrefix);
	
	/**
	 * 
	 * @param id
	 * @param errorCodePrefix
	 */
	void performDelete(long id, String errorCodePrefix);

	void preformAddUser(EmployeeDTO employeedto);
}
