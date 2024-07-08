package com.springSecurityForms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springSecurityForms.employeeDto.EmployeeRegisterDto;
import com.springSecurityForms.model.EmployeeEntity;
import com.springSecurityForms.serviceImpl.EmployeeServiceImpl;

@Controller
@EnableMethodSecurity
@RequestMapping("/register")

public class FormController {
	
	@Autowired
	private EmployeeServiceImpl empService;
	
    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }
    
    //user securing
    @GetMapping("/user/{id}")
    @Secured("USER")
    public EmployeeEntity getSpecificEmpDetails( @PathVariable("id") Long id) {
    	
    	return empService.getEmpDetails(id);
    	
    }
    
    
    // admin
    //inserting employees
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/save")
    public EmployeeEntity saveEmpDetails( @RequestBody EmployeeRegisterDto emp) {
    	
    	return this.empService.save(emp);
    	
    }
    
    

	}


