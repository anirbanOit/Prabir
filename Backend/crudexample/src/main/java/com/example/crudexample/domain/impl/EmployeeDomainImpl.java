package com.example.crudexample.domain.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crudexample.domain.EmployeeDomain;
import com.example.crudexample.domain.entity.Employee;
import com.example.crudexample.domain.repo.EmployeeRepo;

@Service("employeeDomain")
public class EmployeeDomainImpl implements EmployeeDomain {
	@Autowired(required = true)
	private EmployeeRepo employeeRepo;

	@Override
	public List<Employee> getAllUsersProfile() {
		return employeeRepo.findAll();
	}

	@Override
	public Employee getUserProfile(final long id) {
		return employeeRepo.findOne(id);
	}

	@Override
	public void deleteUserProfile(final long id) {
		employeeRepo.delete(id);
	}

	@Override
	public void addUser(Employee employee) {
		employeeRepo.save(employee);
	}
}
