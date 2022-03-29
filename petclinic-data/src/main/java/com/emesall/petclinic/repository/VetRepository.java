package com.emesall.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Vet;

@Repository
public interface VetRepository extends CrudRepository<Vet, Long> {

}
