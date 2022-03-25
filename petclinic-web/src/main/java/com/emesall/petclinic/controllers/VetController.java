package com.emesall.petclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.petclinic.service.VetService;

@Controller
public class VetController {

	private final VetService vetService;

	@Autowired
	public VetController(VetService vetService) {
		super();
		this.vetService = vetService;
	}

	@RequestMapping({ "/vets", "/vets/index", "vets/index.html" })
	public String listVets(Model model) {

		model.addAttribute("vets", vetService.findAll());
		return "vets/index";
	}
}
