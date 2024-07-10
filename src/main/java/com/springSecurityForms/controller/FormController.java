package com.springSecurityForms.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springSecurityForms.model.EmployeeEntity;
import com.springSecurityForms.serviceImpl.EmployeeServiceImpl;

@RestController
@RequestMapping("/api")
public class FormController {
	
	@Autowired
	private EmployeeServiceImpl empService;
	
	
    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Logout succesfully");
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
    public EmployeeEntity saveEmpDetails( @RequestBody EmployeeEntity emp) {
    	
    	return this.empService.saveEmployees(emp);
    	
    }
    
    //list of employees
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/list")
    public List<EmployeeEntity> getAllEmployees() {
        return this.empService.getAllEmployees();
    }
    
    //delete the employees
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete")
    public String deleteEmployee(Long id) {
    	this.empService.deleteEmployee(id);
    	
    	return "employee is deleted in database";
    }
    
    
    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("This is a public endpoint");
    }

    @GetMapping("/user")
    public ResponseEntity<String> userEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return ResponseEntity.ok("Welcome " + currentPrincipalName + ", you have USER access");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return ResponseEntity.ok("Welcome " + currentPrincipalName + ", you have ADMIN access");
    }

	


	}


