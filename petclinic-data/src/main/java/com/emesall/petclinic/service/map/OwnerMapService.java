package com.emesall.petclinic.service.map;

import java.util.Set;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.service.OwnerService;

public class OwnerMapService extends AbstractClassService<Owner, Long> implements OwnerService {

	@Override
	public Owner findById(Long id) {
		return super.findById(id);

	}

	@Override
	public Owner save(Long id, Owner object) {
		return super.save(id, object);
	}

	@Override
	public Set<Owner> findAll() {
		return super.findAll();
	}

	@Override
	public void delete(Owner object) {
		super.delete(object);

	}

	@Override
	public void deleteById(Long id) {
		super.deleteById(id);

	}

	@Override
	public Owner findByLastName(String lastName) {

		return map.values().stream().filter(owner -> owner.getLastName().equals(lastName)).findFirst().orElse(null);

	}

}
