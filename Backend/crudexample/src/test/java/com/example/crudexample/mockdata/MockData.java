package com.example.crudexample.mockdata;

import java.util.ArrayList;
import java.util.List;

import com.example.crudexample.domain.entity.Employee;
import com.example.crudexample.dto.EmployeeDTO;

public class MockData {
	public static List<EmployeeDTO> getStubbedEmployeeDTOList() {
		List<EmployeeDTO> employeeDtos = new ArrayList<>();
		employeeDtos.add(getStubbedEmployeeDTO());
		employeeDtos.add(getStubbedEmployeeDTO());
		return employeeDtos;
	}
	
	public static EmployeeDTO getStubbedEmployeeDTO() {
		EmployeeDTO employeeDto = new EmployeeDTO();
		employeeDto.setAddress("testAddress");
		employeeDto.setAge(1);
		employeeDto.setName("testName");
		employeeDto.setZip("100011");
		return employeeDto;
	}
	
	public static List<Employee> getStubbedEmployeeList() {
		List<Employee> employees = new ArrayList<>();
		employees.add(getStubbedEmployee());
		employees.add(getStubbedEmployee());
		return employees;
	}
	
	public static Employee getStubbedEmployee() {
		Employee employee = new Employee();
		employee.setAddress("testAddress");
		employee.setAge(1);
		employee.setName("testName");
		employee.setZip("100011");
		return employee;
	}
}
