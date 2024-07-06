package com.SpringSecurity.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.SpringSecurity.serviceImpl.EmployeeServiceImpl;
import com.SpringSecurity.serviceImpl.JwtServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
//once coming every request
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
     private  JwtServiceImpl jwtService;
	
	@Autowired
	 private  EmployeeServiceImpl empService;


	    @Override
	    protected void doFilterInternal(
	            @NonNull HttpServletRequest request,
	             @NonNull HttpServletResponse response,
	             @NonNull FilterChain filterChain)
	            throws ServletException, IOException {

	    	
	    	//requesting authorization.
	        String authHeader = request.getHeader("Authorization");

	        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
	            filterChain.doFilter(request,response);
	            return;
	        }
           
	        //requesting from token
	        
	        String token = authHeader.substring(7);
	        
	        //extracting token we need jwt service.
	        String username = jwtService.extractUsername(token);

	        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	        	
	        	//in this case authenticate from user.
	            UserDetails userDetails = empService.loadUserByUsername(username);

	            //if token is valid
                //  taking from userdetails
	            if(jwtService.isValid(token, userDetails)) {
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities()
	                        
	                        //here credentails is null 
	       
	                );

	                authToken.setDetails(
	                        new WebAuthenticationDetailsSource().buildDetails(request)
	                );
	                
	                //passing the authentication token to the securityContext.

	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }
	        filterChain.doFilter(request, response);


	    }
	
	

}
