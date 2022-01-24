package com.emesall.petclinic.service;

import com.emesall.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

	Owner findByLastName(String lastName);

}
