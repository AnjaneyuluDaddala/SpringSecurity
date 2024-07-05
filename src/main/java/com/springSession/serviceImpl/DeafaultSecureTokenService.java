package com.springSession.serviceImpl;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.springSession.model.SecurityToken;
import com.springSession.repository.TokensRepository;
import com.springSession.service.SecurityTokenService;

@Service
public class DeafaultSecureTokenService implements SecurityTokenService{
	
	private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom();
    private static final Charset US_ASCII=Charset.forName("US-ASCII");
	
    @Value("{jdj.secure.token.validity}")
    private int tokenValiditySeconds;
    
	
	@Autowired
	private TokensRepository secureTokenRepo;


	@Override
	public SecurityToken createSecureToken() {
		String tokenValue=new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()));
		
//		byte[] tokenBytes = DEFAULT_TOKEN_GENERATOR.generateKey();
//        String tokenValue = Base64.getUrlEncoder().encodeToString(tokenBytes);
		
        SecurityToken secureToken=new SecurityToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpireAt(LocalDateTime.now().plusSeconds(getTokenValiditySeconds()));
        this.saveSecureToken(secureToken);
		return secureToken;
	}


	@Override
	public void saveSecureToken(SecurityToken token) {
		secureTokenRepo.save(token);
	}


	@Override
	public SecurityToken findByToken(String token) {

		return secureTokenRepo.findByToken(token);
	}


	@Override
	public void removeToken(SecurityToken token) {
		secureTokenRepo.delete(token);
		
	}


	@Override
	public void removeTokenByToken(String token) {
		secureTokenRepo.removeByToken(token);
		
	}


	public int getTokenValiditySeconds() {
		return tokenValiditySeconds;
	}


	public void setTokenValiditySeconds(int tokenValiditySeconds) {
		this.tokenValiditySeconds = tokenValiditySeconds;
	}
	
	
	
	

}
