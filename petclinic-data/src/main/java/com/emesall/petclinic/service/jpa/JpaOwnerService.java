package com.emesall.petclinic.service.jpa;

import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.repository.OwnerRepository;
import com.emesall.petclinic.service.OwnerService;

@Service
public class JpaOwnerService extends JpaAbstractClassService<Owner, OwnerRepository, Long> implements OwnerService {

	public JpaOwnerService(OwnerRepository repository) {
		super(repository);
	}

	@Override
	public Owner findByLastName(String lastName) {

		return repository.findByLastName(lastName);
	}

}
