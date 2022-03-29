package com.emesall.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {

}
