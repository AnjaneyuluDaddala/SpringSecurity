package com.SpringSecurity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SpringSecurity.model.Tokens;

@Repository
public interface TokenRepository extends JpaRepository<Tokens, Integer>{

    
	@Query(value = "SELECT t.* FROM token t JOIN employee e ON t.user_id = e.id WHERE e.id = :userId AND t.is_logged_out = false", nativeQuery = true)
	List<Tokens> findAllAccessTokensByUser(@Param("userId") Integer userId);



    Optional<Tokens> findByAccessToken(String token);

    Optional<Tokens> findByRefreshToken(String token);

}
