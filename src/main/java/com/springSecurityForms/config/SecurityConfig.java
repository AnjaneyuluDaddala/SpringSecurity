package com.springSecurityForms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(requests -> requests
            	.antMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf->csrf.disable())
        
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
            )
            .logout(logout -> logout
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
            	.logoutUrl("/logout")	
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) //a session is created only if required
                .invalidSessionUrl("/login?invalidSession")
            );
    }
    
    
    @Bean
    public PasswordEncoder pswdEncode() {
    	return new BCryptPasswordEncoder();
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
