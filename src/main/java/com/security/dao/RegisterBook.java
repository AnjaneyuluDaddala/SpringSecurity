package com.security.dao;

import java.util.HashSet;
import java.util.Set;

import com.security.model.Role;

public class RegisterBook {
	
	private String lastName;
	private String firstName;
	private String phone;
	private String address;
	private String password;
	private String email;
	private Set<Role> roles = new HashSet<>();
	
	public RegisterBook() {
		
	}



	public RegisterBook(String lastName, String firstName, String phone, String address, String password, String email,
			Set<Role> roles) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.phone = phone;
		this.address = address;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}



	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public Set<Role> getRoles() {
		return roles;
	}



	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}
