package com.springSecurityForms.model;

import org.springframework.data.relational.core.mapping.Table;

@Table(value="User")
public class Employee {
	
	private String username;
	private String password;
	private String role;
	
	public Employee() {
		
	}

	public Employee(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
	

}
