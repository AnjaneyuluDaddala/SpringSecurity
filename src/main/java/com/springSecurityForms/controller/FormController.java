package com.springSecurityForms.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class FormController {
	

//	 @GetMapping("/login")
//	    public String login(@RequestParam(value = "logout", required = false) String logout,
//	                        @RequestParam(value = "invalidSession", required = false) String invalidSession,
//	                        Model model) {
//	        if (logout != null) {
//	            model.addAttribute("logout", true);
//	        }
//	        if (invalidSession != null) {
//	            model.addAttribute("invalidSession", true);
//	        }
//	        return "login";
//	        
//	        
//	    }
	 
	 
   @GetMapping("/login")
   public String getLogin() {
	   
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
	    
	    
//	    @GetMapping("/logout")
//	    public String logoutPage(HttpSession session) {
//	    	session.invalidate();
//	    return "redirect:/login";
//	    }
	    
	    
//	    @GetMapping("/logout")
//	    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	        if (auth != null) {
//	            new SecurityContextLogoutHandler().logout(request, response, auth);
//	        }
//	        return "redirect:/login?logout"; // Redirect to login page with logout parameter
//	    }
//	    
	}

	   



