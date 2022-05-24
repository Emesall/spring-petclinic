package com.emesall.petclinic.events;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.service.VerificationTokenService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	private MessageSource messages;
	private JavaMailSender mailSender;
	private VerificationTokenService tokenService;

	@Autowired
	public RegistrationListener(MessageSource messages, JavaMailSender mailSender,
			VerificationTokenService tokenService) {
		super();
		this.messages = messages;
		this.mailSender = mailSender;
		this.tokenService = tokenService;
	}

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		confirmRegistration(event);

	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		Owner owner = (Owner) event.getPerson();
		String token = UUID.randomUUID().toString();

		tokenService.createAndSaveVerificationToken(owner, token);

		/*String emailAddress = owner.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = event.getAppUrl() + "/confirmRegistration?token=" + token;
		String messageConfirmation = messages.getMessage("regSucc", null, event.getLocale());
		String welcomeMessage=messages.getMessage("welcome", null, event.getLocale());
		try {
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(emailAddress);
			email.setSubject(subject);
			email.setText(welcomeMessage+" "+owner.getFirstName()+" "+owner.getLastName()+"!"+"\r\n"+messageConfirmation+ "\r\n" + "http://localhost:8080" + confirmationUrl);
			mailSender.send(email);
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/

	}

}
