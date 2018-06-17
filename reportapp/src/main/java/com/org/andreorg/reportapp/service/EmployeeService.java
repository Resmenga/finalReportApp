package com.org.andreorg.reportapp.service;

import com.org.andreorg.reportapp.model.Employee;

public interface EmployeeService {
	public Employee findEmployeeByFirstName(String firstName);
	public void saveEmployee(Employee employee);

}
