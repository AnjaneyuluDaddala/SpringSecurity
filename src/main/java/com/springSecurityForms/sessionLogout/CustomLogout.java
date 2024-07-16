package com.springSecurityForms.sessionLogout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogout implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		 // Check if there is an existing session
        if (request.getSession(false) != null) {
            // Invalidate the session if it exists
            request.getSession(false).invalidate();
            System.out.println("Session invalidated: " + request.getSession(false).getId());
        }

        // Set the response to indicate successful logout without creating a new session
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Logout successful");
        response.getWriter().flush();
        response.getWriter().close();
    }
	}
	
	

