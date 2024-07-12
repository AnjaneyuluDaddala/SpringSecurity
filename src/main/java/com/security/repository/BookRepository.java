package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.model.BookUser;

@Repository
public interface BookRepository extends JpaRepository<BookUser, Integer>{
	
	Optional<BookUser> findByEmail(String email);

}
