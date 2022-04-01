package com.emesall.petclinic.controllers;

import java.util.List;

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

	@GetMapping("/find")
	public String findOwners(Model model) {
		model.addAttribute("owner", Owner.builder().build());
		return "owners/findOwners";
	}

	@GetMapping
	public String processFindForm(Owner owner, BindingResult bindingResult, Model model) {

		// if there is no name provided, we show list of all owners
		if (owner.getLastName() == null) {
			owner.setLastName("");
		}

		// find owners by last name
		List<Owner> results = ownerService.findByLastName("%" + owner.getLastName() + "%");

		if (results.isEmpty()) {
			// no owners found
			bindingResult.rejectValue("lastName", "notFound", "not found");

			return "owners/findOwners";
		} else if (results.size() == 1) {
			// 1 owner found
			owner = results.get(0);
			return "redirect:/owners/" + owner.getId();
		} else {
			// multiple owners found
			model.addAttribute("selections", results);
			return "owners/ownersList";
		}
	}

	@GetMapping("/new")
	public String initNewOwnerForm(Model model) {
		model.addAttribute("owner", Owner.builder().build());
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/new")
	public String processNewOwnerForm(@Valid Owner owner, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "owners/createOrUpdateOwnerForm";
		} else {
			Owner savedOwner=ownerService.save(owner);
			return "redirect:/owners/" + savedOwner.getId();

		}

	}

	@GetMapping("/{id}/edit")
	public String initEditOwnerForm(Model model, @PathVariable Long id) {
		model.addAttribute("owner", ownerService.findById(id));
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/{id}/edit")
	public String processEditOwnerForm(@Valid Owner owner, BindingResult bindingResult, @PathVariable Long id) {

		if (bindingResult.hasErrors()) {
			return "owners/createOrUpdateOwnerForm";
		} else {
			owner.setId(id);
			Owner savedOwner=ownerService.save(owner);
			return "redirect:/owners/" + savedOwner.getId();

		}

	}
}
