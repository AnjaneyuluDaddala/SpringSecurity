package com.springSecurityForms.employeeDto;

import java.util.List;

import com.springSecurityForms.model.Role;

public class EmployeeRegisterDto {

	  private String name;
	  
	  private String email;

	  private String password;
	  
	  private int age;
	  
	  private double salary;
	  
	  private List<Role> role;
	  
	  public EmployeeRegisterDto() {
		  
	  }

	public EmployeeRegisterDto(String name, String password, int age, double salary, List<Role>  role,String email) {
		super();
		this.name = name;
		this.password = password;
		this.age = age;
		this.salary = salary;
		this.role = role;
		this.email=email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public List<Role>  getRole() {
		return role;
	}

	public void setRole(List<Role>  role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	  
	  

}
