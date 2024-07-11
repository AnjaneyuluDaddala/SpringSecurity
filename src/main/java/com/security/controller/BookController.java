package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dao.AuthenticationResponse;
import com.security.dao.LoginBook;
import com.security.dao.RegisterBook;
import com.security.model.BookUser;
import com.security.serviceImpl.BookServiceImpl;

@RestController
@RequestMapping("/book")
@EnableGlobalAuthentication
public class BookController {
	
	
	private final BookServiceImpl bookServiceImpl;
	
	

	public BookController(BookServiceImpl bookServiceImpl) {
		super();
		this.bookServiceImpl = bookServiceImpl;
	}
	@GetMapping("/")
	public String home() {
		
		return "home page";
	}
	@GetMapping("/store")
	public String store() {
		
		return "store page";
	}
	@GetMapping("/admin/home")
	public String admin() {
		
		return " admin home page";
	}
	@GetMapping("/client/home")
	public String getClientHome() {
		
		return "client home page";
	}
	
	
	
//	  @Secured("hasRole('ADMIN')")
	  @PostMapping("/register")
	    public ResponseEntity<AuthenticationResponse> save(
	            @RequestBody RegisterBook request
	    ) {
		  bookServiceImpl.register(request);
	        return ResponseEntity.accepted().build();
	    }
	  
	  
	    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	    @PostMapping("/authenticate")
	    public ResponseEntity<AuthenticationResponse> LoginUser(
	            @RequestBody LoginBook request
	    ) {
		  bookServiceImpl.authenticate(request);
	        return ResponseEntity.accepted().build();
	    }
	  
	  
	  
	    @PreAuthorize("hasRole('ADMIN')")
	    @GetMapping("/list")
	    public ResponseEntity<List<BookUser>> findAllBooks() {
	        return ResponseEntity.ok(bookServiceImpl.getAllBooks());
	    } 
	  
	    
	    
	
}
