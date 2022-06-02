package com.emesall.petclinic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.emesall.petclinic.model.Person;

public interface PersonService<T extends Person> extends CrudService<T, Long>{
	
	T findByEmail(String email);
	boolean checkIfExists(T person);
	Page<T> findByLastName(String name,Pageable pageable);
	
}
