package com.org.andreorg.reportapp.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.andreorg.reportapp.model.Employee;
import com.org.andreorg.reportapp.model.Role;
import com.org.andreorg.reportapp.repository.EmployeeRepository;
import com.org.andreorg.reportapp.repository.RoleRepository;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Employee findEmployeeByEmail(String email) {
		return employeeRepository.findByEmail(email);
	}

	@Override
	public void saveEmployee(Employee employee) {
		employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employee.setActive(1);
        Role employeeRole = roleRepository.findByRole("ADMIN");
        employee.setRoles(new HashSet<Role>(Arrays.asList(employeeRole)));
        employeeRepository.save(employee);
	}
	

}
