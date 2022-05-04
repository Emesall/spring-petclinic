package com.emesall.petclinic.service;

import org.springframework.web.multipart.MultipartFile;

import com.emesall.petclinic.model.Pet;

public interface PetService extends CrudService<Pet, Long> {
	
	byte[] getImage(Long petId);
	void saveImage(Long petId, MultipartFile file);
}
