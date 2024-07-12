package com.springSecurityForms.config;

import org.springframework.context.annotation.Configuration;


//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests(request->request
//				.antMatchers("/page").permitAll()
//				.antMatchers("/user/**","/admin/**").authenticated()
//				
//				
//				);
//		http.formLogin(form->form
//				.loginPage("/login")
//				.defaultSuccessUrl("/home")
//				.permitAll());
//		
//		http.logout(logout->logout
//				.permitAll());
//		
//		super.configure(http);
//		
//	}			
//}