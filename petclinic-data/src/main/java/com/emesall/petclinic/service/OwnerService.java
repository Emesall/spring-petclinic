package com.emesall.petclinic.service;

import java.util.List;
import java.util.Optional;

import com.emesall.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long>{

	List<Owner> findByLastName(String lastName);
	boolean checkIfOwnerExists(Owner owner);
	Optional<Owner> findByUsername(String username);

}
