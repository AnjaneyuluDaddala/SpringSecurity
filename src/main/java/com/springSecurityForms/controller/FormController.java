package com.springSecurityForms.controller;


import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {
	
	       
	    @GetMapping("/page") 
	    public String page() { 
	        return "page"; 
	          
	    }
	    
	    
	    
	    @GetMapping("/user")
	    public String userLogin(Model model,Principal prince) {
	    	model.addAttribute("name",prince.getName() );
	    	return "welcome";
	    }

	    @GetMapping("/user/portal")
	    public String adminLogin(Model model,Principal prince) {
	    	model.addAttribute("name", prince.getName());
	    	return "welcome";
	    }
}
