package com.springSecurityForms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(requests -> requests
            		.antMatchers("/session/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
         
            .sessionManagement(session -> session
            		 .maximumSessions(1)
                     .sessionRegistry(sessionRegister())  // it is to track the sessions.
                     .maxSessionsPreventsLogin(true) // prevents new logins when the maximum number of sessions is reached.
            );
    }
    
   
    
    @Bean
    public SessionRegistry sessionRegister() {
    	
    	return new SessionRegistryImpl();   
    	
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