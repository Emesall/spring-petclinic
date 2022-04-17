package com.emesall.petclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

	@GetMapping("/{id}")
	public ModelAndView showOwner(@PathVariable Long id) {

		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject("owner", ownerService.findById(id));
		return mav;
	}

	@GetMapping()
	public String showAccount(Model model, @AuthenticationPrincipal Owner owner) {

		model.addAttribute("owner", ownerService.findById(owner.getId()));
		return "owners/ownerDetails";
	}

	
}
