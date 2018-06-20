package com.org.andreorg.reportapp.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	
	@Override
	public Employee updateEmployee(Employee employee) {
		return employeeRepository.updateEmployee(employee);
	}

	@Override
	public Page<Employee> findAllEmployees(PageRequest pageRequest) {
		return employeeRepository.findAll(pageRequest);
	}

	@Override
	public Page<Employee> findAll(PageRequest of) {
		return employeeRepository.findAll(of);
	}

	@Override
	public Page<Employee> findByFirstName(Pageable pageable, String firstName) {
		return employeeRepository.findByFirstName(pageable, firstName);
	}

	@Override
	public Page<Employee> findByEmail(Pageable pageable, String email) {
		return employeeRepository.findByEmail(pageable, email);
	}

	@Override
	public Page<Employee> findByLastName(Pageable pageable, String lastName) {
		return employeeRepository.findByLastName(pageable, lastName);
	}

	@Override
	public Page<Employee> findByDepartmentID(Pageable pageable, int departmentID) {
		return employeeRepository.findByDepartmentID(pageable, departmentID);
	}

	@Override
	public Page<Employee> findByEmpID(Pageable pageable, long empID) {
		return employeeRepository.findByEmpID(pageable, empID);
	}

	@Override
	public Page<Employee> findBySalary(Pageable pageable, int salary) {
		return employeeRepository.findBySalary(pageable, salary);
	}

	@Override
	public void deleteById(Long id) {
		employeeRepository.deleteById(id);	
	}

	@Override
	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public List<Employee> findAll() {
		return (List<Employee>) employeeRepository.findAll();
	}
}
