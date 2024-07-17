package com.springSecurityForms.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.springSecurityForms.sessionLogout.CustomLogout;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomLogout customLogoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(requests -> requests
            	.antMatchers("/user/**").hasRole("USER")
            	.antMatchers("/admin/*").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
            	.logoutUrl("/logout")	
                .logoutSuccessHandler(customLogoutHandler)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .sessionManagement(session -> session
                .invalidSessionUrl("/session-timeout")
                .maximumSessions(1)
                .expiredUrl("/session-expired")
            );
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = request.getContextPath();
            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                redirectUrl = "/admin/home";
            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
                redirectUrl = "/user/home";
            }
            response.sendRedirect(redirectUrl);
        };
    }

    @Bean
    public PasswordEncoder pswdEncode() {
    	return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
    	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
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
