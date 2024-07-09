package com.springSecurityForms.jwtProvider;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springSecurityForms.model.EmployeeEntity;
import com.springSecurityForms.security.CustomEmployeeServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtProvider {
	
	@Autowired
	private CustomEmployeeServiceImpl empDetailsServ;
	
	
	@Value("${app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${app.jetExpireTime}")
	private int expireTime;
	
	
	public String generateToken(final Authentication auth) {
		
		EmployeeEntity EmpPrincipal =(EmployeeEntity) auth.getPrincipal();
		
		Date now=new Date();
		
		Date expireDate=new Date(now.getTime()+expireTime);
		
		
		 
		String subject = Long.toString(EmpPrincipal.getId());
		
		return Jwts.builder()
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();		
	}
	
	//validate 
	
	public boolean validateToken(String authToken){

		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			 return true;
		}catch(Exception e) {
			return false;
		}
		 		 	
	}
	
		
	
	
	//get userId from token
	public String getUserIdFromJWT(String token) {
		
		Claims claim = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		
		return claim.getSubject();
		
	}
	
	//Returning Token from Header of Authentication
	public String resolveToken(HttpServletRequest request) {
		
		String bearToken = request.getHeader("Authentication");
		if(bearToken !=null && bearToken.startsWith("Bearer ")) {
			return bearToken.substring(7);
			
		}
		return null;
	}
	
	//
	public Authentication getAuthentication(String token) {
		
		UserDetails userDetails= empDetailsServ.loadUserByUsername(getUserIdFromJWT(token));
		
		return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities() );
	}
	

}
