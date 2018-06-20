package com.org.andreorg.reportapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.org.andreorg.reportapp.model.Employee;

public interface EmployeeService {
	public Employee findEmployeeByEmail(String email);
	public Page<Employee> findAll(PageRequest of);
	public void saveEmployee(Employee employee);
	public Employee updateEmployee(Employee employee);
	public Page<Employee> findAllEmployees(PageRequest of);
	public Page<Employee> findByFirstName(Pageable pageable, String searchOptionValue);
	public Page<Employee> findByEmail(Pageable pageable, String searchOptionValue);
	public Page<Employee> findByLastName(Pageable pageable, String searchOptionValue);
	public Page<Employee> findByDepartmentID(Pageable pageable, int parseInt);
	public Page<Employee> findByEmpID(Pageable pageable, long parseLong);
	public Page<Employee> findBySalary(Pageable pageable, int parseInt);
	public void deleteById(Long id);
	public Optional<Employee> findById(Long id);
	public List<Employee> findAll();

}
