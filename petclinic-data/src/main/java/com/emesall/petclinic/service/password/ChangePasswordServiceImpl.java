package com.emesall.petclinic.service.password;

import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Person;
import com.emesall.petclinic.service.Token.ResetPasswordTokenService;
import com.emesall.petclinic.service.email.EmailSender;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {

	private final EmailSender emailSender;
	private final MessageSource messages;
	private final ResetPasswordTokenService tokenService;
	
	@Autowired
	public ChangePasswordServiceImpl(EmailSender emailSender,MessageSource messages, ResetPasswordTokenService tokenService) {
		super();
		this.emailSender = emailSender;
		this.messages=messages;
		this.tokenService=tokenService;
	}



	@Override
	public void sendEmail(Person person, String contextPath, Locale locale) {
		
		String token = UUID.randomUUID().toString();
		tokenService.createAndSaveToken(person, token);
		String subject="Reset password";
		String confirmationUrl = contextPath + "/changePassword?token=" + token;
		String messageConfirmation = messages.getMessage("changePass", null, locale);
		String welcomeMessage = messages.getMessage("welcome", null, locale);
		String message = welcomeMessage + " " + person.getFirstName() + " " + person.getLastName() + "!" + "\r\n"
				+ messageConfirmation + "\r\n" + "http://localhost:8080" + confirmationUrl;
		emailSender.sendEmail(person.getEmail(),subject ,message);

	}

}
