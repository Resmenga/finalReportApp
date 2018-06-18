package com.org.andreorg.reportapp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.org.andreorg.reportapp.model.Employee;

@Repository("employeeRepository")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>, EmployeeRepositoryCustom {
	Employee findByEmail(String email);
}
