package com.emesall.petclinic.service.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Speciality;
import com.emesall.petclinic.repository.SpecialityRepository;
import com.emesall.petclinic.service.SpecialityService;

@Service
@Profile("jpa")
public class JpaSpecialityService extends JpaAbstractClassService<Speciality, SpecialityRepository, Long> implements SpecialityService {

	public JpaSpecialityService(SpecialityRepository repository) {
		super(repository);
	
	}

}
