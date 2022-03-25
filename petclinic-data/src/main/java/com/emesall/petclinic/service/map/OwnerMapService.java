package com.emesall.petclinic.service.map;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.service.OwnerService;

@Service
public class OwnerMapService extends AbstractClassService<Owner, Long> implements OwnerService {

	@Override
	public Owner findById(Long id) {
		return super.findById(id);

	}

	@Override
	public Owner save(Owner object) {
		return super.save(object);
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

		return map.values()
				.stream()
				.filter(owner -> owner.getLastName().equals(lastName))
				.findFirst()
				.orElse(null);

	}

}
