package com.springSecurityForms.config;

import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.SessionEvent;
import org.apache.catalina.SessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.springSecurityForms.customSession.CustomSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(requests -> requests
                .anyRequest().authenticated()
            )
            .csrf(csrf->csrf.disable())
            
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home",true)
                .failureUrl("/login?error=true")
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
            	    .sessionFixation().migrateSession()// creates a new session whenever a user authenticates
                     
            );
        
    }
    
    
    
    @Bean
    public PasswordEncoder pswdEncode() {
    	return new BCryptPasswordEncoder();    
    }
    
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
    	return new HttpSessionEventPublisher();
    }
    
    @Bean
    public HttpSessionListener sessionListener() {
        return new CustomSession();

    }
    
    
    @Bean
    public UserDetailsService userDetailsService() {
    	InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
    	
    	manager.createUser(User
    			.withUsername("anjan")
    			.password(pswdEncode().encode("1234"))
    			.roles("USER")
    			.build());
    	
    	manager.createUser(User
    			.withUsername("admin")
    			.password(pswdEncode().encode("4567"))
    			.roles("ADMIN")
    			.build());
    	
    	return manager;
    }
}
