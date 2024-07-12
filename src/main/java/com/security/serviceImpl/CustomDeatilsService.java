package com.security.serviceImpl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.model.BookUser;
import com.security.model.Role;
import com.security.repository.BookRepository;

@Service
public class CustomDeatilsService implements UserDetailsService{
	
	@Autowired
	private BookRepository bookRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BookUser bookUser = bookRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
		
		return new User(bookUser.getEmail(),bookUser.getPassword(),mapRolesToAuthorities(bookUser.getRoles()));
	}
	  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
	        return roles.stream()
	                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
	                .collect(Collectors.toList());
	    }


}
