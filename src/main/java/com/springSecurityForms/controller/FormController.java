package com.springSecurityForms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FormController {
	

	 @GetMapping("/login")
	    public String login(@RequestParam(value = "logout", required = false) String logout,
	                        @RequestParam(value = "invalidSession", required = false) String invalidSession,
	                        Model model) {
	        if (logout != null) {
	            model.addAttribute("logout", true);
	        }
	        if (invalidSession != null) {
	            model.addAttribute("invalidSession", true);
	        }
	        return "login";
	        
	        
	    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

	    @GetMapping("/public")
	    public String publicPage() {
	        return "new";
	    }
	    
	    @GetMapping("/logout")
	    public String logoutPage() {
	    	
	    return "redirect:/login";
	    }
	}

	   



