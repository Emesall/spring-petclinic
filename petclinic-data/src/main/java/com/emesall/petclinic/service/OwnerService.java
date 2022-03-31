package com.emesall.petclinic.service;

import java.util.List;

import com.emesall.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

	List<Owner> findByLastName(String lastName);

}
