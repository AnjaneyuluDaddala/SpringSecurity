package com.springSecurityForms.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springSecurityForms.CustomSuccessFulHandler;
import com.springSecurityForms.security.CustomEmployeeServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
 @Autowired
 private CustomEmployeeServiceImpl customService;
 
 @Autowired
 private  CustomSuccessFulHandler successHandler;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	
	        http.csrf(csrf -> csrf.disable())
	                .authorizeHttpRequests(requests -> {
	                    requests.antMatchers("/registration/**", "/login/**").permitAll();
	                    requests.anyRequest().authenticated();})
	                
	                .formLogin(login -> login.loginPage("/login")
	                .successHandler(successHandler))
	                .csrf(csrf -> csrf.disable())
	                .logout(logout -> logout.logoutUrl("/logout")
	                .logoutSuccessUrl("/login"))
	                .oauth2Login(login -> login.loginPage("/login").successHandler(successHandler));
	        
	        http.headers(header->header
					.frameOptions(frame->frame.sameOrigin())
					);
	
	        super.configure(http);
	    }
		
	              
	                


	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	     auth.authenticationProvider(daoAuthenticationProvider());
			}

	    
	    @Bean
	    DaoAuthenticationProvider daoAuthenticationProvider() {
	    	DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
	    	dao.setPasswordEncoder(passwordEncoder());
	    	dao.setUserDetailsService(customService);
	    	
	    	return dao;
	    }

	 


		@Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }


}