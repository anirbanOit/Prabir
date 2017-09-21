package com.example.crudexample.domain;

import java.util.List;

import com.example.crudexample.domain.entity.Employee;

public interface EmployeeDomain {

	/**
	 * 
	 * @return List<Employee>
	 */
	List<Employee> getAllUsersProfile();

	/**
	 * 
	 * @param id
	 * @return Employee
	 */
	Employee getUserProfile(long id);

	/**
	 * 
	 * @param id
	 */
	void deleteUserProfile(long id);

	void addUser(Employee employee);

}
