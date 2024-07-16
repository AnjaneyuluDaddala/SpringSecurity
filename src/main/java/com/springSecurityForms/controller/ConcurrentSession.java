package com.springSecurityForms.controller;


import java.util.List;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class ConcurrentSession {
	
	  private final SessionRegistry sessionRegister;

	    public ConcurrentSession(SessionRegistry sessionRegister) {
	        this.sessionRegister = sessionRegister;
	    }
	    
	    @GetMapping("/")
	    public List<Object> getAllSessions() {
	        return sessionRegister.getAllPrincipals();
	    }

//	    @GetMapping("/session-info")
//	    public List<SessionInformation> getSessionInformation(@RequestParam(required = false) String sessionId) {
//	        if (sessionId == null) {
//	            throw new IllegalArgumentException("Session ID cannot be null");
//	        }
//	        return sessionRegister.getAllSessions(sessionId, false);
//	    }
}
