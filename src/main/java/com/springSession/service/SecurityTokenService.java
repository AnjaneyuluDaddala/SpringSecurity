package com.springSession.service;

import com.springSession.model.SecurityToken;

public interface SecurityTokenService {
	
	public  SecurityToken createSecureToken();
	
	public void saveSecureToken(SecurityToken token);
	
	public SecurityToken findByToken(String token);
	
	public void removeToken(SecurityToken token);
	
	public void removeTokenByToken(String token);
	
	

}
