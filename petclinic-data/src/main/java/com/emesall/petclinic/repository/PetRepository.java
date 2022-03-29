package com.emesall.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Pet;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {

}
