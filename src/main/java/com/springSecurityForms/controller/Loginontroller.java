package com.springSecurityForms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springSecurityForms.employeeDto.EmployeeLoginDto;
import com.springSecurityForms.security.CustomEmployeeServiceImpl;

@Controller
@RequestMapping("/login")
public class Loginontroller {
	
	@Autowired
	CustomEmployeeServiceImpl cstmImpl;
	
	@GetMapping
	public String login() {
		return "login";
	}
	
	@PostMapping
	public void loginUser(@RequestBody  EmployeeLoginDto userLoginDTO) {
		System.out.println("UserDTO"+userLoginDTO);
		cstmImpl.loadUserByUsername(userLoginDTO.getUsername());
	}

}
