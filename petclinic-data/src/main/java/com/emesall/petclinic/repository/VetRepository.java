package com.emesall.petclinic.repository;

import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Vet;

@Repository
public interface VetRepository extends PersonRepository<Vet> {

	
}
