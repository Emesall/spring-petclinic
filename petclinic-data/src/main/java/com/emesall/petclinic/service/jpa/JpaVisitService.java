package com.emesall.petclinic.service.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Visit;
import com.emesall.petclinic.repository.VisitRepository;
import com.emesall.petclinic.service.VisitService;

@Service
@Profile("jpa")
public class JpaVisitService extends JpaAbstractClassService<Visit, VisitRepository, Long> implements VisitService {

	public JpaVisitService(VisitRepository repository) {
		super(repository);

	}

}
