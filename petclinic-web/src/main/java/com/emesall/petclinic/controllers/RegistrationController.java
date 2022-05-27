package com.emesall.petclinic.controllers;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.emesall.petclinic.events.OnRegistrationCompleteEvent;
import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.model.RegistrationForm;
import com.emesall.petclinic.model.token.VerificationToken;
import com.emesall.petclinic.service.OwnerService;
import com.emesall.petclinic.service.Token.VerificationTokenService;

@Controller
public class RegistrationController {

	private static final String OWNER_REGISTER_FORM = "owners/registerForm";
	private final OwnerService ownerService;
	private final PasswordEncoder encoder;
	private final ApplicationEventPublisher eventPublisher;
	private final VerificationTokenService tokenService;

	@Autowired
	public RegistrationController(OwnerService ownerService, PasswordEncoder encoder,
			ApplicationEventPublisher eventPublisher, VerificationTokenService tokenService) {
		super();
		this.ownerService = ownerService;
		this.encoder = encoder;
		this.eventPublisher = eventPublisher;
		this.tokenService = tokenService;
	}

	// handle registration of owner
	@GetMapping("/register/owner")
	public String registerFormOwner(Model model) {
		model.addAttribute("registerForm", new RegistrationForm());
		return OWNER_REGISTER_FORM;
	}

	@PostMapping("/register/owner")
	public String processRegistrationOwner(@ModelAttribute("registerForm") @Valid RegistrationForm form,
			BindingResult bindingResult, HttpServletRequest request, Model model) {

		// transform registerForm to owner
		Owner owner = form.DataToOwner(encoder);
		// check if owner exists in database
		if (ownerService.checkIfExists(owner))
			bindingResult.rejectValue("username", "username.exists");
		// check if there are some validations errors
		if (bindingResult.hasErrors())
			return OWNER_REGISTER_FORM;
		else {
			
			ownerService.save(owner);
			// new event to handle email sending
			eventPublisher.publishEvent(
					new OnRegistrationCompleteEvent(owner, request.getLocale(), request.getContextPath()));
			// save owner
			
			return "redirect:/login?registered";
		}

	}

	@GetMapping("/confirmRegistration")
	public String confirmRegistration(Model model, @RequestParam("token") String token) {

		VerificationToken verificationToken = tokenService.getToken(token);
		if (verificationToken == null) {
			System.out.println("Token null");
		}
		Owner owner = (Owner) verificationToken.getPerson();
		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpirationDate().getTime() - cal.getTime().getTime()) <= 0) {
			System.out.println("expired");
		}

		owner.setEnabled(true);

		ownerService.save(owner);
		return "redirect:/login";
	}

}
