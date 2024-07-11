package com.security.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

//@Configuration
//public class bankSecurity extends WebSecurityConfiguration{
	
	
	// predefined table user
	
	/*
	 * @Bean UserDetailsService userDetailsService(DataSource datasource) {
	 * 
	 * return new JdbcUserDetailsManager(datasource); }
	 * 
	 * @Bean PasswordEncoder getPasswordEncoder() { return
	 * NoOpPasswordEncoder.getInstance(); }
	 */
	
	//customized table.

//}
