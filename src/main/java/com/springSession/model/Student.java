package com.springSession.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String collegeName;
	private String password;
	@Column(name="email",unique = true,nullable = false)
	private String email;
	private String course;
	private boolean accountVerified;
	
	@OneToMany(mappedBy = "student")
	private Set<SecurityToken>tokens;
	
	public Student() {
		
	}

	public Student(Long id, String name, String collegeName, String password, String email, String course,boolean account) {
		super();
		this.id = id;
		this.name = name;
		this.collegeName = collegeName;
		this.password = password;
		this.email = email;
		this.course = course;
		this.accountVerified=account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isAccountVerified() {
		return accountVerified;
	}

	public void setAccountVerified(boolean accountVerified) {
		this.accountVerified = accountVerified;
	}
	
	
	
	
	
	

}
