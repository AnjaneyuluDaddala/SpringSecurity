package com.springSession.service;

import javax.mail.MessagingException;

import com.springSession.model.AbstractEmailContext;

public interface EmailService {
	
	void sendEmail(final AbstractEmailContext email) throws MessagingException;

}
