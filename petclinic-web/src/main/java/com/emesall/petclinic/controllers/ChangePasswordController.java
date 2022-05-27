package com.emesall.petclinic.controllers;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.emesall.petclinic.model.ChangePasswordForm;
import com.emesall.petclinic.model.Person;
import com.emesall.petclinic.model.token.ResetPasswordToken;
import com.emesall.petclinic.service.PersonService;
import com.emesall.petclinic.service.Token.ResetPasswordTokenService;
import com.emesall.petclinic.service.password.ChangePasswordService;

@Controller
public class ChangePasswordController {

	private static final String FORGOT_PASSWORD_TEMPLATE = "resetPassword/forgotPassword";
	private static final String CHANGE_PASSWORD_TEMPLATE = "resetPassword/changePassword";
	private final ChangePasswordService changePasswordService;
	private final PersonService<Person> personService;
	private final ResetPasswordTokenService tokenService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public ChangePasswordController(ChangePasswordService changePasswordService, PersonService<Person> personService,
			ResetPasswordTokenService tokenService, PasswordEncoder passwordEncoder) {
		super();
		this.changePasswordService = changePasswordService;
		this.personService = personService;
		this.tokenService = tokenService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/forgotPassword")
	public String initForgotPassword() {

		return FORGOT_PASSWORD_TEMPLATE;

	}

	@PostMapping("/forgotPassword")
	public String processForgotPassword(@ModelAttribute("email") @Valid String email, BindingResult bindingResult,
			HttpServletRequest request) {

		Person person = personService.findByEmail(email);
		if (bindingResult.hasErrors()) {
			return FORGOT_PASSWORD_TEMPLATE;
		} else {
			try {
				changePasswordService.sendEmail(person, request.getContextPath(), request.getLocale());
			} catch (Exception ex) {
				return "redirect:/forgotPassword?problem";
			}
			return "redirect:/forgotPassword?sent";

		}

	}

	@GetMapping("/changePassword")
	public String initChangePassword(Model model, @RequestParam("token") String token) {
		ResetPasswordToken verificationToken = tokenService.getToken(token);
		if (verificationToken == null) {
			System.out.println("Token null");
			return "redirect:/login";
		}

		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpirationDate().getTime() - cal.getTime().getTime()) <= 0) {
			System.out.println("expired");
			return "redirect:/login?expired";
		}
		ChangePasswordForm passwordForm = new ChangePasswordForm();
		passwordForm.setToken(token);
		model.addAttribute("passwordForm", passwordForm);

		return CHANGE_PASSWORD_TEMPLATE;
	}

	@PostMapping("/changePassword")
	public String processChangePassword(@ModelAttribute("passwordForm") @Valid ChangePasswordForm passwordForm,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return CHANGE_PASSWORD_TEMPLATE;
		} else {
			ResetPasswordToken token = tokenService.getToken(passwordForm.getToken());
			Person person = token.getPerson();
			person.setPassword(passwordEncoder, passwordForm.getPassword());
			personService.save(person);
		}
		return "redirect:/login?passChanged";
	}
}
