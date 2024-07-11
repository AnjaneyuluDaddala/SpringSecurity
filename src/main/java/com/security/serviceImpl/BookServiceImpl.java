package com.security.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dao.AuthenticationResponse;
import com.security.dao.LoginBook;
import com.security.dao.RegisterBook;
import com.security.model.BookUser;
import com.security.repository.BookRepository;
import com.security.service.BookServiceInter;

@Service
public class BookServiceImpl implements BookServiceInter {
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private JwtuserDetailsService jwtServ;
	
	@Autowired
	private PasswordEncoder pswd;
	
	@Autowired
	private AuthenticationManager authManager;

	@Override
	public AuthenticationResponse register(RegisterBook book) {
		
		BookUser bookUser =new BookUser();
		bookUser.setFirstName(book.getFirstName());
		bookUser.setLastName(book.getLastName());
		bookUser.setAddress(book.getAddress());
		bookUser.setEmail(book.getEmail());
		bookUser.setPassword(pswd.encode(book.getPassword()));
		bookUser.setPhone(book.getPhone());
		bookUser.setRole(book.getRole());
		
		String generateToken = jwtServ.generateToken(bookUser);
		
		AuthenticationResponse auth=new AuthenticationResponse(generateToken);

		return auth;
		
	}

	@Override
	public List<BookUser> getAllBooks() {
		
		return bookRepo.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		
		 bookRepo.deleteById(id);
	}
	
	@Override
	public AuthenticationResponse authenticate(LoginBook book) {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						book.getEmail(),
						book.getPassword())
				);
		
		BookUser bookUser = bookRepo.findByEmail(book.getEmail()).orElseThrow();

		String generateToken = jwtServ.generateToken(bookUser);
		
		AuthenticationResponse auth=new AuthenticationResponse(generateToken);

		return auth;
		
		
	}
	
	

}
