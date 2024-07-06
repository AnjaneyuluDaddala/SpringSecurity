package com.SpringSecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringSecurity.model.AuthenticationResponse;
import com.SpringSecurity.model.Employee;
import com.SpringSecurity.serviceImpl.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class EmployeeController {
	
	   private final AuthenticationService authService;

	    public EmployeeController(AuthenticationService authService) {
	        this.authService = authService;
	    }


	    @PostMapping("/register")
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
}
