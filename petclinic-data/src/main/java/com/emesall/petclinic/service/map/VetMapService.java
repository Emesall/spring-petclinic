package com.emesall.petclinic.service.map;

import java.util.Set;

import com.emesall.petclinic.model.Vet;
import com.emesall.petclinic.service.VetService;

public class VetMapService extends AbstractClassService<Vet, Long> implements VetService {

	@Override
	public Vet findById(Long id) {
		return super.findById(id);
	}

	@Override
	public Vet save(Long id,Vet object) {
		return super.save(id, object);
	}

	@Override
	public Set<Vet> findAll() {
		return super.findAll();
	}

	@Override
	public void delete(Vet object) {
		super.delete(object);
		
	}

	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
		
	}

}
