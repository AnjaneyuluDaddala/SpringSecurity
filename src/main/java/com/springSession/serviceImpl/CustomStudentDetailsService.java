package com.springSession.serviceImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springSession.model.Student;
import com.springSession.repository.StudentRepository;


@Service
public class CustomStudentDetailsService implements UserDetailsService{
	@Autowired
	private StudentRepository Repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Student student = this.Repo.findByEmail(email);
		if(student==null) {
			throw new UsernameNotFoundException(email);
		}
		//new Change
		  boolean enabled = !(student.isAccountVerified());
		  
		UserDetails user=User.withUsername(student.getEmail())
				             .password(student.getPassword())
				             .disabled(enabled)
				             .authorities("USER").build();
		
		return user;
	}

}
