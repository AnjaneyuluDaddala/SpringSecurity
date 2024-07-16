package com.springSecurityForms.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.springSecurityForms.logoutHandler.LogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LogoutHandler logoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(requests -> requests
            		.antMatchers("/admin/**").hasRole("ADMIN")
            		.antMatchers("/user/**").hasRole("USER")
            		.anyRequest().permitAll()
                    
            )
            .csrf(csrf->csrf.disable())
            .headers(header->header.frameOptions(frame->frame.sameOrigin()))
            
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler())
                .failureHandler(customAuthenticationFailureHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutHandler)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
         
            .sessionManagement(session -> session
            		
            		.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)    
                    .maximumSessions(1)
                    .sessionRegistry(sessionRegister())// it is to track the sessions.
                    .maxSessionsPreventsLogin(false)  
                   
                
                    
       
            );
        
        http.sessionManagement(session->session
        		.invalidSessionUrl("/invalidSession")
        		);
          
    }
    
   
    
    @Bean
    public SessionRegistry sessionRegister() {
    	
    	return new SessionRegistryImpl();   
    	
    }
    
    
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = request.getContextPath();

            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                redirectUrl = "/admin/home";
            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
                redirectUrl = "/user/home";
            }

            response.sendRedirect(redirectUrl);
        };
    }
    
    
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect("/login?error=true");
        };
    }
    
    @Bean
    public PasswordEncoder pswdEncode() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService getUserDetails() {
    	InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
    	manager.createUser(
    			User.withUsername("anjan")
    			.password(pswdEncode().encode("1234"))
    			.roles("USER")
    			.build());
    	
    	manager.createUser(
    			User.withUsername("ajay")
    			.password(pswdEncode().encode("ajay"))
    			.roles("USER")
    			.build());
    	
    	manager.createUser(
    			User.withUsername("admin")
    			.password(pswdEncode().encode("admin"))
    			.roles("ADMIN")
    			.build());
    	
    	return manager;
    			
    	
    }
    
}