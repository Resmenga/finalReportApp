package com.org.andreorg.reportapp.service;

import com.org.andreorg.reportapp.model.Employee;

public interface EmployeeService {
	public Employee findEmployeeByEmail(String email);
	public void saveEmployee(Employee employee);
	public Employee updateEmployee(Employee employee);
	//TODO: remove this method from here
	public static boolean isNumeric(String str) {
	    if (str == null) {
	        return false;
	    }
	    int sz = str.length();
	    for (int i = 0; i < sz; i++) {
	        if (Character.isDigit(str.charAt(i)) == false) {
	            return false;
	        }
	    }
	    return true;
	}
}
