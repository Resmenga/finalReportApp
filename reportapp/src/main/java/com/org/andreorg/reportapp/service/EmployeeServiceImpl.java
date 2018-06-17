package com.org.andreorg.reportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.andreorg.reportapp.model.Employee;
import com.org.andreorg.reportapp.repository.EmployeeRepository;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Employee findEmployeeByFirstName(String firstName) {
		return employeeRepository.findByFirstName(firstName);
	}

	@Override
	public void saveEmployee(Employee employee) {
		employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employee.setActive(1);
        
        employeeRepository.save(employee);
		
	}
	

}
