package com.emesall.petclinic.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.model.RegistrationForm;
import com.emesall.petclinic.service.OwnerService;
import com.emesall.petclinic.service.VetService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	private static final String OWNER_REGISTER_FORM = "owners/registerForm";
	private final OwnerService ownerService;
	private final VetService vetService;
	private final PasswordEncoder encoder;

	@Autowired
	public RegistrationController(OwnerService ownerService, VetService vetService, PasswordEncoder encoder) {
		super();
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.encoder = encoder;
	}

	// handle registration of owner
	@GetMapping("/owner")
	public String registerFormOwner(Model model) {
		model.addAttribute("registerForm", new RegistrationForm());
		return OWNER_REGISTER_FORM;
	}

	@PostMapping("/owner")
	public String processRegistrationOwner(@ModelAttribute("registerForm") @Valid RegistrationForm form,
			BindingResult bindingResult) {

		Owner owner = form.DataToOwner(encoder);
		if (ownerService.checkIfOwnerExists(owner)) {
			bindingResult.rejectValue("username", "username.exists");
		} else {
			ownerService.save(owner);
			return "redirect:/login";
		}
		return OWNER_REGISTER_FORM;

	}

	// handle registration of vet
	@GetMapping("/vet")
	public String registerFormVet() {
		return "vets/registerForm";
	}

	@PostMapping("/vet")
	public String processRegistrationvet() {

		return "redirect:/vets";
	}
}
