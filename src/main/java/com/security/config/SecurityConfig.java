package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Autowired
	private JwtAuthenticationFilter jwtRequestFilter;
	
	@Autowired
	private AuthenticationProvider userDetailsProvider;
	  
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((requests)->
		   
		    
			requests.requestMatchers("/book/home","/book/store","/book/authenticate/**","/book/register/**").permitAll() 
			.anyRequest().authenticated())
		    .authenticationProvider(userDetailsProvider)
		    .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		    .headers(httpSecurityHeadersConfigurer->httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
	        .exceptionHandling(httpExcepConfig->httpExcepConfig.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
	        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
          
		
	http.csrf(csrf->csrf.disable());
	
		return http.build();
		
	}
	
	
	
	
//	@Bean
//	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//	    http
//	        
//	        .authorizeHttpRequests(requests -> requests
//	                .requestMatchers(HttpMethod.GET, "/", "/static/**", "/index.html", "/api/users/me").permitAll()
//	                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//	                .requestMatchers(HttpMethod.GET, "/api/users/login", "/api/users/{username}", "/api/users/logout", "/api/customers", "/api/storages").authenticated()
//	                .requestMatchers(HttpMethod.POST, "/api/customers", "/api/storages").authenticated()
//	                .requestMatchers(HttpMethod.PUT, "/api/customers/{id}", "/api/storages/{id}").authenticated()
//	                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}", "/api/storages/{id}", "/api/customers/{id}").authenticated()
//	                .anyRequest().denyAll())
//	        .httpBasic();
//	    return http.build();
//	}

	
	
	
	
//	 @Bean
//	    @Order(1)
//	    public SecurityFilterChain configurePublicEndpoints(HttpSecurity http) throws Exception {
//	        http.securityMatcher(OPTIONS,"/openapi/openapi.yml").csrf().disable()
//	            .authorizeHttpRequests((requests) -> requests
//	                .anyRequest().permitAll() // allow CORS option calls for Swagger UI
//	    );
//	        return http.build();
//	      }
//	    
//	    @Bean
//	    Order(2)
//	      public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//	        http.securityMatcher("/**")
//	            .csrf().disable()
//	            .authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
//	            .httpBasic();
//	        return http.build();
//	      }
}
