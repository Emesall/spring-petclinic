package com.emesall.petclinic.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.service.OwnerService;

@RequestMapping("/owners") // prefix, added to every each of mapping presented below f.e /owners/index
@Controller
public class OwnerController {

	private final OwnerService ownerService;

	@Autowired
	public OwnerController(OwnerService ownerService) {
		super();
		this.ownerService = ownerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}



	@GetMapping()
	public String showAccount(Model model, @AuthenticationPrincipal Owner owner) {

		model.addAttribute("owner", ownerService.findById(owner.getId()));

		return "owners/showOwner";
	}
	
	@GetMapping("/edit")
	public String initEditOwnerForm(Model model, @AuthenticationPrincipal Owner owner) {
		model.addAttribute("owner", ownerService.findById(owner.getId()));
		
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/edit")
	public String processEditOwnerForm(@Valid Owner owner, BindingResult bindingResult, @AuthenticationPrincipal Owner auth_owner) {

		if (bindingResult.hasErrors()) {
			return "owners/createOrUpdateOwnerForm";
		} else {
			owner.setId(auth_owner.getId());
			ownerService.save(owner);
			return "redirect:/owners";

		}
	}

	
}
