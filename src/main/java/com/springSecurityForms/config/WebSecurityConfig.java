package com.springSecurityForms.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
 @Autowired
 @Qualifier("customEmployeeServiceImpl")
 private UserDetailsService customService;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests(request->request
				.antMatchers("/home/**","/h2-console/**","/save").permitAll()
				.antMatchers("/user/**").hasRole("USER")
				.antMatchers("/admin/**").hasRole("ADMIN")
				);
				
		http.formLogin(login->login
				.usernameParameter("username")
				.permitAll()
				);
		http.logout(logout->logout
				.permitAll()
				);
		
		http.csrf(csrf->csrf
				.ignoringAntMatchers("/h2-console/**")
				);
		http.headers(header->header
				.frameOptions(frame->frame.sameOrigin())
				);
	
		super.configure(http);
	}



	    
	    @Bean
	    DaoAuthenticationProvider daoAuthenticationProvider() {
	    	DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
	    	dao.setPasswordEncoder(passwordEncoder());
	    	dao.setUserDetailsService(customService);
	    	
	    	return dao;
	    }

	    
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	     auth.authenticationProvider(daoAuthenticationProvider());
			}



		@Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }


}