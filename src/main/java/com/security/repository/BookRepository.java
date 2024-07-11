package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.BookUser;

public interface BookRepository extends JpaRepository<BookUser, Integer>{
	
	Optional<BookUser> findByEmail(String email);

}
