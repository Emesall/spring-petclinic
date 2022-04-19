package com.emesall.petclinic.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.petclinic.exceptions.NotFoundException;
import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.model.PetType;
import com.emesall.petclinic.service.OwnerService;
import com.emesall.petclinic.service.PetService;
import com.emesall.petclinic.service.PetTypeService;

@Controller
@RequestMapping("/admin/owners/{ownerId}")
public class PetController {

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

	private final PetService petService;
	private final OwnerService ownerService;
	private final PetTypeService petTypeService;

	public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
		this.petService = petService;
		this.ownerService = ownerService;
		this.petTypeService = petTypeService;
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return petTypeService.findAll();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
		return ownerService.findById(ownerId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/pets/new")
	public String initCreationForm(@ModelAttribute("owner") Owner owner, Model model) {
		Pet pet = new Pet();
		owner.addPet(pet);
		model.addAttribute("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;

	}

	@PostMapping("/pets/new")
	public String processCreationForm(@ModelAttribute("owner") Owner owner, @Valid @ModelAttribute("pet") Pet pet,
			BindingResult result, Model model) {

		if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.checkIfPresent(pet)) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		owner.addPet(pet);
		if (result.hasErrors()) {

			model.addAttribute("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {

			petService.save(pet);
			return "redirect:/admin/owners/" + owner.getId();
		}

	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable Long petId, Model model, @PathVariable("ownerId") Long ownerId) {
		Pet pet = petService.findById(petId);
		if (!pet.getOwner().getId().equals(ownerId)) {
			throw new NotFoundException("Pet not Found");
		}	
		model.addAttribute("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Valid @ModelAttribute("pet") Pet pet, BindingResult result,
			@ModelAttribute("owner") Owner owner, Model model) {

		pet.setOwner(owner);
		if (result.hasErrors()) {

			model.addAttribute("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {

			petService.save(pet);
			return "redirect:/admin/owners/" + owner.getId();
		}
	}

}