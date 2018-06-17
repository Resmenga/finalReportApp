package com.org.andreorg.reportapp.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EmpID", updatable = false, nullable = false)
	private int empID;

	@Column(name = "FName", updatable = false, nullable = false)
	private String firstName;

	@Column(name = "LName", updatable = false, nullable = false)
	private String lastName;

	@Column(name = "Salary")
	private int salary;

	@Column(name = "DeptID")
	private int departmentID;

	@Column(name = "PhNumber")
	private int phoneNumber;
	
	@Column(name = "Address")
	private String address;
	
	@Temporal(TemporalType.DATE)
	private Date crtTimeStamp;

	public Employee() {
	}
	
	public Employee(String firstName, String lastName, int salary, int departmentID,
			int phoneNumber, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.departmentID = departmentID;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.crtTimeStamp = new Date();
	}

	public int getEmpID() {
		return empID;
	}

	public void setEmpID(int empID) {
		this.empID = empID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCrtTimeStamp() {
		return crtTimeStamp;
	}

	public void setCrtTimeStamp(Date crtTimeStamp) {
		this.crtTimeStamp = crtTimeStamp;
	}

	@Override
	public String toString() {
		return "Employee [empID=" + empID + ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary
				+ ", departmentID=" + departmentID + ", phoneNumber=" + phoneNumber + ", address=" + address
				+ ", crtTimeStamp=" + crtTimeStamp + "]";
	}
	
	
}
