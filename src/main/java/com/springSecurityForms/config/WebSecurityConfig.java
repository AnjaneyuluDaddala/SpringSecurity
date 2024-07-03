package com.springSecurityForms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	
/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(request->request
				.antMatchers("/home/**").permitAll()
				.antMatchers("/user/**").hasRole("USER")
				.antMatchers("/admin/**").hasRole("ADMIN")
				
				);
	
		super.configure(http);
	}
	
	*/
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(dataSource);
//	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests(request->request
				.antMatchers("/home/**","/h2-console/**").permitAll()
				.antMatchers("/user/**").hasRole("USER")
				.antMatchers("/admin/**").hasRole("ADMIN")
				);
				
		http.formLogin(login->login
				.usernameParameter("username")
				.loginPage("/login")
				.permitAll()
				);
		http.logout(logout->logout
				.logoutRequestMatcher( new AntPathRequestMatcher("/logout"))
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

	
	//InMemoryUserDetailsManager
	
	/*
	
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
	 
	 */

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    
	    //JdbcUserDetails
	    
	    @Bean
		public UserDetailsService getDetails() {
			
		UserDetails user1=User.withUsername("anjan").password(passwordEncoder().encode("1234"))
				                             .roles("USER").build();
		UserDetails admin=User.withUsername("admin").password(passwordEncoder().encode("4567"))
				                             .roles("ADMIN").build();
		
		JdbcUserDetailsManager jdbc=new JdbcUserDetailsManager(dataSource);
		
		jdbc.createUser(user1);
		jdbc.createUser(admin);
		
			
			return jdbc;
			
			
}
}