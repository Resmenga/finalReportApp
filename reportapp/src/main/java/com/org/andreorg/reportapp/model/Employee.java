package com.org.andreorg.reportapp.model;


import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;
import org.springframework.data.annotation.Transient;

@Entity
@DynamicUpdate
@Table(name = "Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id", updatable = false, nullable = false)
	private int empID;

	@Column(name = "first_name", updatable = false, nullable = false)
	private String firstName;

	@Column(name = "last_name", updatable = false, nullable = false)
	private String lastName;

	@Column(name = "salary")
	private int salary;

	@Column(name = "dept_id")
	private int departmentID;

	@Column(name = "ph_number")
	private int phoneNumber;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "email")
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	
	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	private String password;
	
	@Column(name = "active")
	private int active;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "employee_role", joinColumns = @JoinColumn(name = "emp_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Employee [empID=" + empID + ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary
				+ ", departmentID=" + departmentID + ", phoneNumber=" + phoneNumber + ", address=" + address
				+ ", crtTimeStamp=" + crtTimeStamp + "]";
	}
	
	
}
