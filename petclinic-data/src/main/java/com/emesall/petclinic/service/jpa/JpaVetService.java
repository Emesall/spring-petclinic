package com.emesall.petclinic.service.jpa;

import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Vet;
import com.emesall.petclinic.repository.VetRepository;
import com.emesall.petclinic.service.VetService;

@Service
public class JpaVetService extends JpaAbstractClassService<Vet, VetRepository, Long> implements VetService {

	public JpaVetService(VetRepository repository) {
		super(repository);

	}

}
