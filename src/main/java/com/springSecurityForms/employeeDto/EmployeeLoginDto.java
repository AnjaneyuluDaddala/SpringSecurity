package com.springSecurityForms.employeeDto;

public class EmployeeLoginDto {
	
	private String email;
	private String password;
	private int otp;
	
	public EmployeeLoginDto() {
		
	}

	public EmployeeLoginDto(String email, String password,int otp) {
		super();
		this.email = email;
		this.password = password;
		this.otp=otp;
	}

	public String getUsername() {
		return email;
	}

	public void setUsername(String username) {
		this.email = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}



}
