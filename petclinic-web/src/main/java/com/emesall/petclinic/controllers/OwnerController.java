package com.emesall.petclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@RequestMapping({ "", "/", "/index", "/index.html" })
	public String listOwners(Model model) {

		model.addAttribute("owners", ownerService.findAll());
		return "owners/index";
	}
}
