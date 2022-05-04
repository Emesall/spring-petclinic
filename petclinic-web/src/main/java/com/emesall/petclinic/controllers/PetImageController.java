package com.emesall.petclinic.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.emesall.petclinic.exceptions.NotFoundException;
import com.emesall.petclinic.model.Admin;
import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.model.Person;
import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.service.PetService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PetImageController {

	private PetService petService;

	@Autowired
	public PetImageController(PetService petService) {
		super();
		this.petService = petService;
	}

	@GetMapping("/pets/{petId}/fetchImage")
	public void fetchImage(@PathVariable Long petId, HttpServletResponse httpResponse,
			@AuthenticationPrincipal Person person) throws IOException {

		byte[] image = petService.getImage(petId);
		if (!(person instanceof Admin)) {
			Pet pet = petService.findById(petId);
			if (!pet.getOwner().getId().equals(person.getId())) {
				throw new NotFoundException("Pet not Found");
			}
		}
		if (image != null) {
			log.debug("Loading image from database");
			httpResponse.setContentType("image/jpeg");

			InputStream is = new ByteArrayInputStream(image);
			IOUtils.copy(is, httpResponse.getOutputStream());

		}

	}

	@PostMapping("/pets/{petId}/image")
	public String handleImageUpload(@PathVariable Long petId, @RequestParam("imagefile") MultipartFile file,
			HttpServletRequest request, @AuthenticationPrincipal Person person) {
		log.debug("Request from view to controller to save image..");
		if (!(person instanceof Admin)) {
			Pet pet = petService.findById(petId);
			if (!pet.getOwner().getId().equals(person.getId())) {
				throw new NotFoundException("Pet not Found");
			}
		}
		petService.saveImage(petId, file);
		return "redirect:" + request.getHeader("referer");
	}
}
