package com.emesall.petclinic.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.service.OwnerService;

@RequestMapping("/admin")
@ConfigurationProperties(prefix="owners")
@Controller
public class AdminController {

	private int pageSize = 5;
	private final OwnerService ownerService;

	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Autowired
	public AdminController(OwnerService ownerService) {
		super();
		this.ownerService = ownerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/owners/find")
	public String findOwners(Model model) {
		model.addAttribute("owner", Owner.builder().build());
		return "admin/findOwners";
	}

	@GetMapping("/owners/page/{pageNum}")
	public String processFindForm(@ModelAttribute Owner owner, BindingResult bindingResult, @PathVariable int pageNum,
			@RequestParam(required = false) String sortField, @RequestParam(required = false) String sortDir,
			Model model) {

		// if there is no name provided, we show list of all owners
		if (owner.getLastName() == null) {
			owner.setLastName("");
		}
		if (sortField == null)
			sortField = "id";
		if (sortDir == null)
			sortDir = "asc";
		Page<Owner> results = ownerService.findByLastName("%" + owner.getLastName() + "%", PageRequest.of(pageNum - 1,
				pageSize, sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending()));
		// add additional info
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", results.getTotalPages());
		model.addAttribute("totalResults", results.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		// find owners by last name
		if (results.isEmpty()) {
			// no owners found
			bindingResult.rejectValue("lastName", "notFound", "not found");

			return "admin/findOwners";
		} else if (results.getContent().size() == 1 && pageNum == 1) {
			// 1 owner found and not the last page
			owner = results.getContent().get(0);
			return "redirect:/admin/owners/" + owner.getId();
		} else {
			// multiple owners found
			model.addAttribute("selections", results.getContent());
			return "admin/ownersList";
		}
	}

	@GetMapping("/owners/new")
	public String initNewOwnerForm(Model model) {
		model.addAttribute("owner", Owner.builder().build());
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/owners/new")
	public String processNewOwnerForm(@Valid @ModelAttribute Owner owner, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "owners/createOrUpdateOwnerForm";
		} else {
			Owner savedOwner = ownerService.save(owner);
			return "redirect:/admin/owners/" + savedOwner.getId();

		}

	}

	@GetMapping("/owners/{id}/edit")
	public String initEditOwnerForm(Model model, @PathVariable Long id) {
		model.addAttribute("owner", ownerService.findById(id));
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/owners/{id}/edit")
	public String processEditOwnerForm(@Valid Owner owner, BindingResult bindingResult, @PathVariable Long id) {

		if (bindingResult.hasErrors()) {
			return "owners/createOrUpdateOwnerForm";
		} else {
			owner.setId(id);

			Owner savedOwner = ownerService.save(owner);
			return "redirect:/admin/owners/" + savedOwner.getId();

		}
	}

	@GetMapping("/owners/{id}")
	public ModelAndView showOwner(@PathVariable Long id) {

		ModelAndView mav = new ModelAndView("admin/ownerDetails");
		mav.addObject("owner", ownerService.findById(id));
		return mav;
	}

}
