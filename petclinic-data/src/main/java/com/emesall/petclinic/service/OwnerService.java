package com.emesall.petclinic.service;

import java.util.Set;

import com.emesall.petclinic.model.Owner;

public interface OwnerService {

	Owner findByLastName(String lastName);

	Owner findByID(Long id);

	Owner save(Owner owner);

	Set<Owner> findAll();
	
}
