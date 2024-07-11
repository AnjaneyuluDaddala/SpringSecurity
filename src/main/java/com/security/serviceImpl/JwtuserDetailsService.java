package com.security.serviceImpl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtuserDetailsService {
	
	 private static final long EXPIRATION_TIME_IN_MS = 3 * 60 * 60 * 1000;
	

	//main method
	public String extractUsername(String token) {
		
		return extractClaim(token,Claims::getSubject);
		
		
	}
	
	// 1->first method.
	


	private Claims extractAllClaims(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
					
	}
	
	//inside 1-> first method

	private Key getSignInKey() {
		String secretKey="RiTUPPNINYIXp/4FwDQakeGboIS/NQt48HaZBMB4EKoxKMWsuyJlgDj10XNIE7Ed\r\n";
		
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
		
		
	}
	
	//main method inside
	// and using the extract all claims
	private <T>  T extractClaim(String token, Function<Claims,T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	
	//Generate Token
	
	public String generateToken(Map<String,Object> extractClaims,UserDetails details) {
		Date current = new Date(System.currentTimeMillis());
		
		Date expireTime=new Date(current.getTime()+EXPIRATION_TIME_IN_MS);
			
		return Jwts.builder()
				.setClaims(extractClaims)
				.setSubject(details.getUsername())
				.setIssuedAt(current)
				.setExpiration(expireTime)
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
				
		
	}
	
	//generate Token by userDetails
	
	public String generateToken(UserDetails userDetails) {
		
		return generateToken(new HashMap<>(), userDetails);
	}
	
	
	//is token valid 
	public boolean isTokenValid(String token,UserDetails userDetails) {
		
		final String username=extractUsername(token);
		
		return (username.endsWith(userDetails.getPassword()) && !isTokenExpired(token));
	}
	//inside
	
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
		
	}
	
	//inside
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
