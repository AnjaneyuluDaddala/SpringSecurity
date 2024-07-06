package com.SpringSecurity.serviceImpl;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.SpringSecurity.model.Employee;
import com.SpringSecurity.repository.TokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl {
	
	
	   @Value("${application.security.jwt.secret-key}")
	    private String secretKey;

	    @Value("${application.security.jwt.access-token-expiration}")
	    private long accessTokenExpire;

	    @Value("${application.security.jwt.refresh-token-expiration}")
	    private long refreshTokenExpire;

        @Autowired
	    private  TokenRepository tokenRepository;


        
        //extracting the username from token
	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    
     // validating the token.
	    public boolean isValid(String token, UserDetails user) {
	        String username = extractUsername(token);

	        boolean validToken = tokenRepository
	                .findByAccessToken(token)
	                .map(t -> !t.isLoggedOut())
	                .orElse(false);

	        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
	    }

	    public boolean isValidRefreshToken(String token, Employee emp) {
	        String username = extractUsername(token);

	        boolean validRefreshToken = tokenRepository
	                .findByRefreshToken(token)
	                .map(t -> !t.isLoggedOut())
	                .orElse(false);
	        
	        
	        

	        return (username.equals(emp.getUsername())) && !isTokenExpired(token) && validRefreshToken;
	    }

	    
	    //token is expired or not
	    private boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }
	    
	    //extracting the expiration time from token

	    private Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    
	    //extracting specific property from the token.
	    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
	        Claims claims = extractAllClaims(token);
	        return resolver.apply(claims);
	    }

	    
	    //extracting payload
	    private Claims extractAllClaims(String token) {
	        return Jwts
	                .parser()
	                .verifyWith(getSigninKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }


	    public String generateAccessToken(Employee emp) {
	        return generateToken(emp, accessTokenExpire);
	    }

	    public String generateRefreshToken(Employee emp) {
	        return generateToken(emp, refreshTokenExpire );
	    }

	    
	    //generating token
	    private String generateToken(Employee emp, long expireTime) {
	        String token = Jwts
	                .builder()
	                .subject(emp.getUsername())
	                .issuedAt(new Date(System.currentTimeMillis()))
	                .expiration(new Date(System.currentTimeMillis() + expireTime ))
	                .signWith(getSigninKey())
	                .compact();

	        return token;
	    }

	    
	    //createing signin key.
	    private SecretKey getSigninKey() {
	        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }


}
