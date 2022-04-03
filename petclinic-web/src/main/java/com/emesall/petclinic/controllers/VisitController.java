package com.emesall.petclinic.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.model.Visit;
import com.emesall.petclinic.service.PetService;
import com.emesall.petclinic.service.VisitService;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

	private static final String VIEWS_VISIT_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";

	private final PetService petService;
	private final VisitService visitService;

	@Autowired
	public VisitController(PetService petService, VisitService visitService) {
		super();
		this.petService = petService;
		this.visitService = visitService;

	}

	@ModelAttribute("pet")
	public Pet findPet(@PathVariable("petId") Long petId) {
		return petService.findById(petId);
	}

	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/visits/new")
	public String initCreationForm(@ModelAttribute("pet") Pet pet, Model model) {

		Visit visit = new Visit();
		model.addAttribute("visit", visit);
		return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;

	}

	@PostMapping("/visits/new")
	public String processCreationForm(@ModelAttribute("pet") Pet pet, @Valid @ModelAttribute("visit") Visit visit,
			BindingResult result, Model model) {

		visit.setPet(pet);
		if (result.hasErrors()) {

			model.addAttribute("visit", visit);
			return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;
		} else {

			visitService.save(visit);
			return "redirect:/owners/{ownerId}";
		}

	}

}
