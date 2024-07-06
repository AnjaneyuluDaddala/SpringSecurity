package com.springSecurityForms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(request->request
				.antMatchers("/page").permitAll()
				.antMatchers("/user/**","/admin/**").authenticated()
				);
		http.formLogin(form->form
				.permitAll());
		
		http.logout(logout->logout
				.permitAll());
		
		super.configure(http);
		
	}			
}