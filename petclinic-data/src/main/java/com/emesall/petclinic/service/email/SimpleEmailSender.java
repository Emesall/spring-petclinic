package com.emesall.petclinic.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailSender implements EmailSender {

	private JavaMailSender mailSender;
	
	
	@Autowired
	public SimpleEmailSender(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}



	@Override
	public void sendEmail(String emailAddress, String subject, String message) throws MailException{
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(emailAddress);
		email.setSubject(subject);
		email.setText(message);
		mailSender.send(email);

	}

}
