package com.springSecurityForms.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FormController {
	

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
						@RequestParam(value = "expired", required = false) String expired,
						Model model) {
		if (error != null) {
			model.addAttribute("errorMsg", "Max sessions reached. Please log out from another session.");
			
		} 
		
		return "login";
	}
	

    

//	
//	@GetMapping("/login")
//	 public String login() {
//		return "login";
//	}

    @GetMapping("/user/home")
    public String userHome(Model model,Principal prince) {
    	model.addAttribute("name", prince.getName());
        return "home";
    }
    
    @GetMapping("/admin/home")
    public String adminHome(Model model,Principal prince) {
    	model.addAttribute("name", prince.getName());
        return "home";
    }
    

  @GetMapping("/public")
  public String publicPage() {
	        return "new";
	    }
	    
  
  @GetMapping("/session-expired")
  public String sessionExpired() {
	  return "session-expired";
  }
	    
	   
	}

	   

/*
 * 
 * 
  @GetMapping("/login")
public String login(Model model, HttpServletRequest request) {
    // Example condition for maximum sessions reached
    if (isMaxSessionsReached(request)) {
        model.addAttribute("errorMsg", "Max sessions reached. Please log out from another session.");
    }
    return "login";
}

private boolean isMaxSessionsReached(HttpServletRequest request) {
    // Your logic to check if maximum sessions are reached
    return false;
}

*/





