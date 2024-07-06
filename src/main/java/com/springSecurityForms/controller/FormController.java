package com.springSecurityForms.controller;


import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {
	
	    
	    @GetMapping("/page") 
	    public String home() { 
	        return "page"; 
	          
	    }
	    
	    
	    
	    @GetMapping("/user")
	    public String userLogin(Model model,Principal prince) {
	    	model.addAttribute("name", prince.getName());
	    	return "welcome";
	    }

	    @GetMapping("/admin")
	    public String adminLogin(Model model,Principal prince) {
	    	model.addAttribute("name",prince.getName() );
	    	return "welcome";
	    }
}
