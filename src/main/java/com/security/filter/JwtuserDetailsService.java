package com.security.filter;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.security.model.BookUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtuserDetailsService {
	
	private static final long EXPIRATION_TIME_IN_MS = 3 * 60 * 60 * 1000; // 3 hours

	// Main method to extract the username from the token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Method to extract all claims from the token
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	// Method to get the signing key
	private Key getSignInKey() {
		String secretKey = "RiTUPPNINYIXp/4FwDQakeGboIS/NQt48HaZBMB4EKoxKMWsuyJlgDj10XNIE7Ed"; // Ensure this is a base64-encoded string
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Method to extract a specific claim from the token
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Generate a token with extra claims
	public String generateToken(Map<String, Object> extractClaims, BookUser book) {
		Date current = new Date(System.currentTimeMillis());
		Date expireTime = new Date(current.getTime() + EXPIRATION_TIME_IN_MS);
		
		return Jwts.builder()
				.setClaims(extractClaims)
				.setSubject(book.getEmail())
				.setIssuedAt(current)
				.setExpiration(expireTime)
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	// Generate a token using BookUser details
	public String generateToken(BookUser book) {
		return generateToken(new HashMap<>(), book);
	}

	// Validate the token
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Check if the token is expired
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Extract the expiration date from the token
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}
