package com.springSecurityForms.controller;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.springSecurityForms.model.LoginStudent;

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
	    
	    @GetMapping("/login")
	    public String showLoginForm(Model model, @CookieValue(value = "username", defaultValue = "") String username) {
	    	
	    	model.addAttribute("loginStudent",new LoginStudent(username) );
	        return "login";
	    }
	    
	    @PostMapping("/login")
        public String checkingLogin(@ModelAttribute("loginStudent") LoginStudent student,
        		                           HttpServletResponse response) {
	    	
	    	Cookie cookie=new Cookie("username",student.getUsername());
	    	cookie.setMaxAge(60*60*24);
	    	response.addCookie(cookie);
	    	
	    	
	    	return "redirect:/home";
	    }
	    
	    @GetMapping("/home")
         public String homePage() {
	    	return "home";
	    }
	   
}



