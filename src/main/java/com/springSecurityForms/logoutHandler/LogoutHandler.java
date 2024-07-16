package com.springSecurityForms.logoutHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutHandler implements LogoutSuccessHandler{
	
	@Autowired
	@Lazy
	private SessionRegistry sessionRegister;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		 // Check if there is an existing session
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
            System.out.println("Session invalidated: " + request.getSession(false).getId());
            sessionRegister.removeSessionInformation(request.getSession().getId());
        } 
        // Set the response to indicate successful logout without creating a new session
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Logout successful");
        response.getWriter().flush();
        response.getWriter().close();
    }
	}
