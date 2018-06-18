package com.org.andreorg.reportapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.org.andreorg.reportapp.model.Employee;

@Repository("employeeRepository")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>, EmployeeRepositoryCustom {
	Employee findByEmail(String email);
	Page<Employee> findByFirstName(Pageable of, String firstName);
	Page<Employee> findByLastName(Pageable pageable, String lastName);
	Page<Employee> findByEmail(Pageable pageable, String email);
	Page<Employee> findByDepartmentID(Pageable pageable, int departmentID);
	Page<Employee> findByEmpID(Pageable pageable, Long empID);
	Page<Employee> findBySalary(Pageable pageable, int salary);
}
