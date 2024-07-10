package com.springSecurityForms.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springSecurityForms.model.EmployeeEntity;
import com.springSecurityForms.model.Role;
import com.springSecurityForms.repository.EmployeeRepository;

@Service
@Transactional
public class CustomEmployeeServiceImpl implements UserDetailsService{
	
	@Autowired
	private EmployeeRepository empRepo;
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		   EmployeeEntity emp = empRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	        return new User(emp.getUsername(),emp.getPassword(), mapRolesToAuthorities(emp.getRoles()));
	}
	
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

}
