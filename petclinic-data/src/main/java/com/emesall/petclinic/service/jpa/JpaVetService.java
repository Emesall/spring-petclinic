package com.emesall.petclinic.service.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Vet;
import com.emesall.petclinic.repository.VetRepository;
import com.emesall.petclinic.service.VetService;

@Service
@Profile("jpa")
public class JpaVetService extends JpaAbstractPersonService<Vet> implements VetService {

	public JpaVetService(VetRepository repository) {
		super(repository);

	}

	
}
