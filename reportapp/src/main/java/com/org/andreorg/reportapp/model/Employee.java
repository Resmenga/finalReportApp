package com.org.andreorg.reportapp.model;


import java.util.Date;
import java.util.Set;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@DynamicUpdate
@Table(name = "Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id", updatable = false, nullable = false)
	private Long empID;

	@Column(name = "first_name", updatable = false, nullable = false)
	@NotEmpty(message = "*Please provide a first name")
	private String firstName;

	@Column(name = "last_name", updatable = false, nullable = false)
	@NotEmpty(message = "*Please provide a last name")
	private String lastName;

	@Column(name = "salary", nullable = false)
	@Min(1)
    @Max(999999999)
	private int salary;

	@Column(name = "dept_id", nullable = false)
	@Min(1)
    @Max(999999999)
	private int departmentID;

	@Column(name = "ph_number", nullable = false)
	@Min(1)
    @Max(999999999)
	private int phoneNumber;
	
	@Column(name = "address", nullable = false)
	@NotEmpty(message = "*Please provide an address")
	private String address;
	
	@Column(name = "email", nullable = false)
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	
	@Column(name = "password", nullable = false)
	@Length(min = 5, message = "*Password must have at least 5 characters")
	@NotEmpty(message = "*Please provide a password")
	private String password;
	
	@Column(name = "active", nullable = false)
	private int active;
	
	@ManyToMany
	@JoinTable(name = "employee_role", joinColumns = @JoinColumn(name = "emp_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@Temporal(TemporalType.DATE)
	private Date crtTimeStamp;

	public Employee() {
	}
	
	public Employee(String firstName, String lastName, int salary, int departmentID,
			int phoneNumber, String address, String email, String password, int active) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.departmentID = departmentID;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;
		this.password = password;
		this.active = active;
		this.crtTimeStamp = new Date();
	}

	public Long getEmpID() {
		return empID;
	}

	public void setEmpID(Long empID) {
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
				+ ", email=" + email + ", password=" + password + ", active=" + active + ", roles=" + roles
				+ ", crtTimeStamp=" + crtTimeStamp + "]";
	}
}
