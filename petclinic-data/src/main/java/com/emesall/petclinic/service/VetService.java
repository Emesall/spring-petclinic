package com.emesall.petclinic.service;

import java.util.Set;

import com.emesall.petclinic.model.Vet;

public interface VetService {
	Vet findByID(Long id);

	Vet save(Vet vet);

	Set<Vet> findAll();
}
