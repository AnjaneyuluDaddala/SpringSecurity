package com.springSecurityForms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    
    
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(requests -> requests
                .antMatchers("/page/**", "/h2-console/**").permitAll()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        http.formLogin(login -> login
                .loginPage("/login")
                .usernameParameter("username")
                .successHandler((request,response,auth)->{
                	
                	if(auth != null && auth.isAuthenticated() && !auth.getAuthorities().isEmpty()) {
                	   	if(auth.getAuthorities().stream().anyMatch(r->r.getAuthority().equals("ROLE_USER"))) {
                    		response.sendRedirect("/user");
                    		
                    	}else if(auth.getAuthorities().stream().anyMatch(r->r.getAuthority().equals("ROLE_ADMIN"))) {
                    		response.sendRedirect("/admin");
                 
                    	}
                		
                	}else {
                		response.sendRedirect("/login");
                	}
                	
               

                	
                })
                .failureUrl("/login?error=true")
               
                .permitAll()
        );

        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll()
        );

        http.csrf(csrf -> csrf    // Disable CSRF protection for H2 console
                .ignoringAntMatchers("/h2-console/**")
        );

        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())  // Allow H2 console to be accessed in an iframe
        );
    }

    


    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}




	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    //JDBCdetails manager of class extends JdbcDaoImpl implements UserDetailsManger.
    //JdbcDaoImpl  extends JdbcDaoSupport implements UserDetailsService.
    
    
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
