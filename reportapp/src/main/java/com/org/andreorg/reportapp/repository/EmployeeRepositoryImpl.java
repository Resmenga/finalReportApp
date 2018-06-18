package com.org.andreorg.reportapp.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.org.andreorg.reportapp.model.Employee;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Transactional
	public Employee updateEmployee(Employee employee) {
		Employee existingEmployee = entityManager.find(Employee.class, employee.getEmpID());

		existingEmployee.setAddress(employee.getAddress());
		existingEmployee.setDepartmentID(employee.getDepartmentID());
		existingEmployee.setPhoneNumber(employee.getPhoneNumber());
		existingEmployee.setSalary(employee.getSalary());
		return existingEmployee;

	}

}
