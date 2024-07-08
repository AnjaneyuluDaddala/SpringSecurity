package com.springSecurityForms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springSecurityForms.model.EmployeeEntity;
import com.springSecurityForms.serviceImpl.EmployeeServiceImpl;

@RestController
public class FormController {
	
	@Autowired
	private EmployeeServiceImpl empService;
	
	      
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
	    @PostMapping("/save")
	    public EmployeeEntity saveDetails(@RequestBody EmployeeEntity employee) {
	    	
	    	return this.empService.saveEmployees(employee);
	    	
	    }
}
