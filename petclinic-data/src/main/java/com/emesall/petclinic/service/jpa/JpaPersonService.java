package com.emesall.petclinic.service.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Person;
import com.emesall.petclinic.repository.PersonRepository;
import com.emesall.petclinic.service.PersonService;

@Service
@Profile("jpa")
public class JpaPersonService extends JpaAbstractPersonService<Person> implements PersonService<Person> {

	public JpaPersonService(PersonRepository<Person> repository) {
		super(repository);
	}

}
