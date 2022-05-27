package com.emesall.petclinic.events;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.emesall.petclinic.model.Person;
import com.emesall.petclinic.service.Token.VerificationTokenService;
import com.emesall.petclinic.service.email.EmailSender;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	private MessageSource messages;
	private EmailSender emailSender;
	private VerificationTokenService tokenService;

	@Autowired
	public RegistrationListener(MessageSource messages, EmailSender emailSender,
			VerificationTokenService tokenService) {
		super();
		this.messages = messages;
		this.tokenService = tokenService;
		this.emailSender = emailSender;
	}

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		confirmRegistration(event);

	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		Person person = event.getPerson();
		String token = UUID.randomUUID().toString();

		tokenService.createAndSaveToken(person, token);

		String emailAddress = person.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = event.getAppUrl() + "/confirmRegistration?token=" + token;
		String messageConfirmation = messages.getMessage("regSucc", null, event.getLocale());
		String welcomeMessage = messages.getMessage("welcome", null, event.getLocale());
		String message = welcomeMessage + " " + person.getFirstName() + " " + person.getLastName() + "!" + "\r\n"
				+ messageConfirmation + "\r\n" + "http://localhost:8080" + confirmationUrl;
		try {
			// emailSender.sendEmail(emailAddress, subject, message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
