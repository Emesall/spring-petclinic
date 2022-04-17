package com.emesall.petclinic.service;

import java.util.List;

import com.emesall.petclinic.model.Person;

public interface PersonService<T extends Person> extends CrudService<T, Long>{
	
	List<T> findByLastName(String lastName);
	boolean checkIfExists(T person);
	
}
