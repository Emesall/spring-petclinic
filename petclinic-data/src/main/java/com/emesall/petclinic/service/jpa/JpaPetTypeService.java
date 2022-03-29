package com.emesall.petclinic.service.jpa;

import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.PetType;
import com.emesall.petclinic.repository.PetTypRepository;
import com.emesall.petclinic.service.PetTypeService;

@Service
public class JpaPetTypeService extends JpaAbstractClassService<PetType, PetTypRepository, Long> implements PetTypeService {

	public JpaPetTypeService(PetTypRepository repository) {
		super(repository);
	}

}
