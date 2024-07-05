package com.springSession.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class StudentDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message= "{validation.name}")
	private String name;
	@NotEmpty(message= "{validation.college.name}")
	private String collegeName;
	@NotEmpty(message= "{validation.password}")
	private String password;
	
	@Email(message= "{validation.email}")
	private String email;
	
	@NotEmpty(message= "{validation.course}")
	private String course;

	
	public StudentDto() {
		
	}

	public StudentDto(String name, String collegeName, String password, String email, String course) {
		super();
		this.name = name;
		this.collegeName = collegeName;
		this.password = password;
		this.email = email;
		this.course = course;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
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

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}


	
	
	
	

}
