package com.emesall.petclinic.service.jpa;

import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.repository.PetRepository;
import com.emesall.petclinic.service.PetService;

@Service
public class JpaPetService extends JpaAbstractClassService<Pet, PetRepository, Long> implements PetService {

	
	public JpaPetService(PetRepository repository) {
		super(repository);
		
	}

}
