package com.springSecurityForms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(request->request
				.antMatchers("/page").permitAll()
				.antMatchers("/user/**").hasAnyRole("USER","ADMIN")
				.antMatchers("/admin/**").hasRole("ADMIN")
				);
		http.formLogin(form->form
				.permitAll());
		
		http.logout(logout->logout
				.permitAll());
		
		super.configure(http);
		
	}
	
	  
	
	//InMemoryUserDetailsManager
	
	
	
	 @Bean
	    public UserDetailsService userDetailsService() {
	        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	        manager.createUser(User.withUsername("anjan")
	            .password(passwordEncoder().encode("1234"))
	            .roles("USER")
	            .build());
	        manager.createUser(User.withUsername("admin")
	            .password(passwordEncoder().encode("4567"))
	            .roles("ADMIN")
	            .build());
	        return manager;
	    }
	 
	 

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	  

	
}
