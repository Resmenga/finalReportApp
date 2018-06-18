package com.org.andreorg.reportapp.service;

import com.org.andreorg.reportapp.model.Employee;

public interface EmployeeService {
	public Employee findEmployeeByEmail(String email);
	public void saveEmployee(Employee employee);
	public Employee updateEmployee(Employee employee);

}
