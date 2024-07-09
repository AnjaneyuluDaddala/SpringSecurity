package com.SpringSecurity.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringSecurity.model.AuthenticationResponse;
import com.SpringSecurity.model.Employee;
import com.SpringSecurity.repository.EmployeeRepository;
import com.SpringSecurity.serviceImpl.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class EmployeeController {
	
	   private final AuthenticationService authService;
	   private final EmployeeRepository repo;

	    public EmployeeController(AuthenticationService authService,EmployeeRepository repo) {
	        this.authService = authService;
	        this.repo=repo;
	    }


	    @PostMapping("/register")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<AuthenticationResponse> register(
	            @RequestBody Employee request
	            ) {
	        return ResponseEntity.ok(authService.register(request));
	    }
	    
	    

	    @PostMapping("/login")
	    public ResponseEntity<AuthenticationResponse> login(
	            @RequestBody Employee request
	    ) {
	        return ResponseEntity.ok(authService.authenticate(request));
	    }
	    
	    
	    @PostMapping("/refresh_token")
	    public ResponseEntity refreshToken(
	            HttpServletRequest request,
	            HttpServletResponse response
	    ) {
	        return authService.refreshToken(request, response);
	    }
	    
	   // @Secured("USER")
	    
	    
	    @GetMapping("/user/{id}")
	    @PreAuthorize("hasRole('USER')")
	    public Employee getUserById( @PathVariable("id")  Integer id) {
	    	Employee employee= this.repo.findById(id).orElseThrow();	
	    	return employee; 	
	    	
	    }
	    
	    
	    @GetMapping("/admin_only")
	    @PreAuthorize("hasRole('ADMIN')")
	    public List<Employee> getMangerById() {
	    	List<Employee> employees = this.repo.findAll();    	
	    	return employees;
	    }
}



