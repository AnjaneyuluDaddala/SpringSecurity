package com.springSession.model;

import java.security.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name="secureTokens")
public class SecurityToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String token;
	
	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp timestamp;
	
	
	@Basic(optional = false)
	@Column(updatable = false)
	private LocalDateTime expireAt;
	
	@ManyToOne
	@JoinColumn(name="std_id",referencedColumnName = "id")
	private Student student;
	
	@Transient
	private boolean isExpired;
	
	public SecurityToken() {
		
	}
	
	

	public SecurityToken(Long id, String token, Timestamp timestamp, LocalDateTime expireAt, Student student,
			boolean isExpired) {
		super();
		this.id = id;
		this.token = token;
		this.timestamp = timestamp;
		this.expireAt = expireAt;
		this.student = student;
		this.isExpired = isExpired;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public LocalDateTime getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(LocalDateTime expireAt) {
		this.expireAt = expireAt;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}
	
	
	

}
