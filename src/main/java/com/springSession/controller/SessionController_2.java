package com.springSession.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController_2 {
	
	@GetMapping("/session/new_1")
	public String home(Principal prince) {
		
		return "home "+prince.getName();
		
	}
	
	@GetMapping
	public String sessionLogin(@RequestParam(value="error" , defaultValue = "false")boolean loginError,
		                  @RequestParam(value="invalid-session" , defaultValue = "false")boolean invalidSession,
		                   final Model model) {
		if(loginError) {
			
		}
		
		if(invalidSession) {
			// model.addAttribute("invalidSession","You already have an active session .we do not allow multiple active sessions");
			
			 model.addAttribute("invalidSession", "session is expired,please re-login");
		}
		model.addAttribute("forgotPaswword","password" );
		
		return "login";
		
	}

}
