package com.emesall.petclinic.service;

import java.util.Set;

import com.emesall.petclinic.model.Pet;

public interface PetService {
	Pet findByID(Long id);

	Pet save(Pet pet);

	Set<Pet> findAll();
}
