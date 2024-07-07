package com.springSecurityForms.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class FormController {
	
	
	   @GetMapping("/login")
	    public String login() {
	    	return "login";
	    	
	    }
    
    @GetMapping("/page")
    public String dashboard() {
    	return "page";
    	
    }
    
   
    
    @GetMapping("/admin")
    public String getAdmin(Model model,Principal prince) {
    	
    	model.addAttribute("name", prince.getName());
    	return "welcome";
    	
    }
    
    @GetMapping("/user")
    public String getUser(Model model,Principal prince) {
    	model.addAttribute("name", prince.getName());
    	return "welcome";
    	
    }
    
    


}
