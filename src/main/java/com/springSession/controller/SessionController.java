package com.springSession.controller;



import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springSession.exception.UserAlreadyExistException;
import com.springSession.model.StudentDto;
import com.springSession.service.StudentServiceInter;

@Controller
public class SessionController {
	
	      
	    @GetMapping("/login") 
	    public String login() { 
	        return "login"; 
	          
	    }
	    
	    
	    
	    
	    
	    /*
	    
	    @GetMapping("/login")
	    public String loginPage(@RequestParam(value = "error", required = false) String error,
	                            Model model) {
	        if (error != null) {
	            model.addAttribute("errorMessage", "Invalid username or password");
	        }
	        return "login";
	    }
	    
	    */
	    
	    @GetMapping("/home")
	    public String homePage(Model model, Principal principal,HttpServletRequest request) {
	    	
	        String username = principal.getName();
	        
	        model.addAttribute("username", username);
	        return "Form/home";
	    }
	    
	    //session logout option-1
	    
	    @GetMapping("/logout")
	    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null) {
	            new SecurityContextLogoutHandler().logout(request, response, auth);
	        }
	        return "redirect:/login?logout";
	        
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
	    
	    
	    
	    
	    @GetMapping("/session")
		public String process(Model model, HttpSession session) {
			@SuppressWarnings("unchecked")
			List<String> messages = (List<String>) session.getAttribute("session_msgs");

			if (messages == null) {
				messages = new ArrayList<>();
			}
			model.addAttribute("sessionMessages", messages);

			return "session";
		}

		@PostMapping("/session/persistMessage")
		public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
			@SuppressWarnings("unchecked")
			List<String> messages = (List<String>) request.getSession().getAttribute("session_msgs");
			if (messages == null) {
				messages = new ArrayList<>();
				request.getSession().setAttribute("session_msgs", messages);
			}
			messages.add(msg);
			request.getSession().setAttribute("session_msgs", messages);
			return "redirect:/session";
		}

		@PostMapping("/session/destroy")
		public String destroySession(HttpServletRequest request) {
			request.getSession().invalidate();
			return "redirect:/session";
		}
		
		
		//new 
		
		@Autowired
	    private StudentServiceInter studentService;

	    @GetMapping("/register")
	    public String register(final Model model){
	        model.addAttribute("studentData", new StudentDto());
	        return "register";
	    }

	    @PostMapping("/register")
	    public String userRegistration(final @Valid @ModelAttribute("studentData")  StudentDto studentData, final BindingResult bindingResult, final Model model){
	        if(bindingResult.hasErrors()){
	           model.addAttribute("studentForm", studentData);
	            return "register";
	        }
	        try {
	        	studentService.register(studentData);
	        }catch (UserAlreadyExistException e){
	            bindingResult.rejectValue("email", "studentData.email","An account already exists for this email.");
	             model.addAttribute("studentForm", studentData);
	            return "register";
	        }
	        return "redirect:/login";
	    }
}
