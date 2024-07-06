package com.SpringSecurity.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringSecurity.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements UserDetailsService{
	
	
	@Autowired
    private  EmployeeRepository empRepo;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return empRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
	}

}
