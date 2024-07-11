package com.security.service;

import java.util.List;

import com.security.dao.AuthenticationResponse;
import com.security.dao.LoginBook;
import com.security.dao.RegisterBook;
import com.security.model.BookUser;

public interface BookServiceInter {
	
	AuthenticationResponse register(RegisterBook book);
	AuthenticationResponse authenticate(LoginBook book);
	void deleteById(Integer id);
    List<BookUser> getAllBooks();

}
