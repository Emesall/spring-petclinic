package com.emesall.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
	Owner findByLastName(String name);

}
