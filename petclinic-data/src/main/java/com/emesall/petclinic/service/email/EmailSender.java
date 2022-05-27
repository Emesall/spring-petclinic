package com.emesall.petclinic.service.email;

import org.springframework.mail.MailException;

public interface EmailSender {

	void sendEmail(String emailAddress, String subject, String message) throws MailException;
	
}
