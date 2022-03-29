package com.emesall.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

}
