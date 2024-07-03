package com.springSecurityForms.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {
	
	      
	    @GetMapping("/login") 
	    public String login() { 
	        return "login"; 
	          
	    }
	    
	    @GetMapping("/home") 
	    public String home() { 
	        return "home"; 
	          
	    }
	    
	    
	    
	    @GetMapping("/user")
	    public String userLogin(Model model) {
	    	model.addAttribute("name", "user");
	    	return "welcome";
	    }

	    @GetMapping("/admin")
	    public String adminLogin(Model model) {
	    	model.addAttribute("name", "admin");
	    	return "welcome";
	    }
}
