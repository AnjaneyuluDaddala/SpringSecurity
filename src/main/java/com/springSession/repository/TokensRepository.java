package com.springSession.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springSession.model.SecurityToken;

@Repository
public interface TokensRepository extends JpaRepository<SecurityToken, Long>{
	
	//it is expire or not valid and userDetails
	SecurityToken findByToken(final String token);
	//if email is verified removing the token
	Long removeByToken(String token);
	
	
	
}
