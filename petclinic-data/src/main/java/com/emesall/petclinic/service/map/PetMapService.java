package com.emesall.petclinic.service.map;

import java.util.Set;

import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.service.PetService;

public class PetMapService extends AbstractClassService<Pet, Long> implements PetService{

	@Override
	public Pet findById(Long id) {
		return super.findById(id);
	}

	@Override
	public Pet save(Long id,Pet object) {
		return super.save(id, object);
	}

	@Override
	public Set<Pet> findAll() {
		return super.findAll();
	}

	@Override
	public void delete(Pet object) {
		super.delete(object);
		
	}

	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
		
	}

}
