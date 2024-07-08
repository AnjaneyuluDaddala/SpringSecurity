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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.springSecurityForms.security.CustomEmployeeServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
 @Autowired
 private CustomEmployeeServiceImpl customService;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests(request->request
				  .antMatchers("/api/admin/**").hasRole("ADMIN") // more specific
	              .antMatchers("/api/user/**").hasRole("USER") // more specific
	              .antMatchers("/api/public/**").permitAll()
	              
	                
				);
				
		http.formLogin(login->login
				.usernameParameter("username")
				.loginPage("/api/login")
				.permitAll()
				);
		http.logout(logout->logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                .logoutSuccessUrl("/api/login?logout")
				);
		
		http.csrf(csrf->csrf
				.disable()
				);
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