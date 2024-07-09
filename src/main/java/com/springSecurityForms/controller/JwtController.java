package com.springSecurityForms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springSecurityForms.jwtProvider.JwtProvider;
import com.springSecurityForms.model.EmployeeEntity;
import com.springSecurityForms.model.LoginDto;
import com.springSecurityForms.repository.EmployeeRepository;

@RestController
@RequestMapping("jwts")
public class JwtController {
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtProvider provider;
	
	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto){
		
		Authentication auth=authManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword() )
				
				);
		
		 SecurityContextHolder.getContext().setAuthentication(auth);
	        String jwt = provider.generateToken(auth);

	        EmployeeEntity EmpDetails = (EmployeeEntity) auth.getPrincipal();
	        
	        
	        return ResponseEntity.ok("new");
	       
	}
}


