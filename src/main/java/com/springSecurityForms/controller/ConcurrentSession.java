package com.springSecurityForms.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class ConcurrentSession {
	
	  private final SessionRegistry sessionRegister;

	    public ConcurrentSession(SessionRegistry sessionRegister) {
	        this.sessionRegister = sessionRegister;
	    }
	    

@GetMapping
public List<SessionInformation> getActiveSessions() {
	
Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
	   Object principal = authenticate.getPrincipal();
	   
	    if (principal instanceof UserDetails) {
	    	UserDetails userDetails = (UserDetails) principal;
		
	}else {
		 return sessionRegister.getAllSessions(principal, false);
	}
	    
	    return sessionRegister.getAllSessions(principal, false);

	    }

}
