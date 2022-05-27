package com.emesall.petclinic.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.emesall.petclinic.model.Person;
import com.emesall.petclinic.service.PersonService;
import com.emesall.petclinic.service.password.ChangePasswordService;

@Controller
public class ChangePasswordController {

	private static final String FORGOT_PASSWORD_TEMPLATE = "resetPassword/forgotPassword";
	private static final String CHANGE_PASSWORD_TEMPLATE = "resetPassword/changePassword";
	private final ChangePasswordService changePasswordService;
	private final PersonService<Person> personService;

	@Autowired
	public ChangePasswordController(ChangePasswordService changePasswordService, PersonService<Person> personService) {
		super();
		this.changePasswordService = changePasswordService;
		this.personService = personService;
	}

	@GetMapping("/forgotPassword")
	public String initForgotPassword() {

		return FORGOT_PASSWORD_TEMPLATE;

	}

	@PostMapping("/forgotPassword")
	public String processForgotPassword(@ModelAttribute("email") @Valid String email, BindingResult bindingResult, HttpServletRequest request) {
		
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
	public String initChangePassword() {

		return CHANGE_PASSWORD_TEMPLATE;
	}

	@PostMapping("/changePassword")
	public String processChangePassword() {

		return null;
	}
}
