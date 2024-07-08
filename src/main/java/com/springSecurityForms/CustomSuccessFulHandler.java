package com.springSecurityForms;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.springSecurityForms.employeeDto.EmployeeRegisterDto;
import com.springSecurityForms.model.EmployeeEntity;
import com.springSecurityForms.repository.EmployeeRepository;
import com.springSecurityForms.serviceImpl.EmployeeServiceImpl;

@Component
public class CustomSuccessFulHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private EmployeeServiceImpl empServ;
	

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		
		String redirectUrl = null;
		if(authentication.getPrincipal() instanceof DefaultOAuth2User) {
			
		DefaultOAuth2User  userDetails = (DefaultOAuth2User ) authentication.getPrincipal();
		
		
         String username = userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login")+"@gmail.com" ;
          if(empRepo.findByEmail(username).get() == null) {
        	  
        	  EmployeeRegisterDto emp = new EmployeeRegisterDto();
        	  

        	  emp.setName(userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login"));
        	  emp.setEmail(username);
        	//  emp.setPassword(emp.get);
        	  emp.setRole(emp.getRole());
        /*
        	  
        	  user.setEmail_id(username);
        	  user.setName(userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login"));
        	  user.setPassword(("Dummy"));
        	  user.setRole("USER");
        	  userService.save(user);
        	  
        	  */
        	  
        	  empServ.save(emp);
          }
		}  redirectUrl = "/dashboard";
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

	}

