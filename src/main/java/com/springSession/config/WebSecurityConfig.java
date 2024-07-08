package com.springSession.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests(request -> request
				.antMatchers( "/h2-console/**", "/session/**").permitAll()
				.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/admin/**").hasRole("ADMIN"));

		http.formLogin(login -> login.
				usernameParameter("username").loginPage("/login")
				.successHandler((request, response, auth) -> {
					if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
						response.sendRedirect("/admin");
					} else if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER"))) {
						response.sendRedirect("/user");
					} else {
						response.sendRedirect("/login");
					}
				}).permitAll());

		http.logout(logout -> logout.
				logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login").deleteCookies("dummyCookie").permitAll());

		http.rememberMe(remember -> remember.
				userDetailsService(getDetails()).tokenValiditySeconds(200000000)
				.useSecureCookie(true).tokenRepository(null).rememberMeCookieName("custom-remember")
				.rememberMeCookieDomain("domain"));
		
		http.csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"));
		
		http.headers(header -> header.frameOptions(frame -> frame.sameOrigin()));
//		http.requiresChannel(channel->channel
//				.antMatchers("/**").requiresSecure()
//				);		

		http.sessionManagement(session -> session
//				 .invalidSessionUrl("/login?invalid-session=true") //session timeout
				
				.sessionFixation().migrateSession()
				.invalidSessionUrl("/session-expired")
				.maximumSessions(1).maxSessionsPreventsLogin(true)
				.expiredUrl("/login?expired=true")
		  

		);

		super.configure(http);
	}

	// concurrent session control

	/*
	 * 
	 * When a user that is already authenticated tries to authenticate again,
	 * 
	 * It can either invalidate the active session of the user and authenticate the
	 * user again with a new session, or allow both sessions to exist concurrently.
	 */

	// session management

	// eg:If customer is not some duartion of time ,we are login the customer once
	// again

	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry register = new SessionRegistryImpl();
		return register;

	}

	@Bean
   public HttpSessionEventPublisher sessionPublisher() {
		return new HttpSessionEventPublisher();
	}
	
	

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/resources/**","/h2-console/**");
		
	}

		@Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	// JdbcUserDetails

	@Bean
	public UserDetailsService getDetails() {

		UserDetails user1 = User.withUsername("anjan").password(passwordEncoder().encode("1234")).roles("USER").build();
		UserDetails user2 = User.withUsername("ajay").password(passwordEncoder().encode("ajay")).roles("USER").build();

		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("4567")).roles("ADMIN")
				.build();

		JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);

		jdbc.createUser(user1);
		jdbc.createUser(user2);
		jdbc.createUser(admin);

		return jdbc;

	}
}