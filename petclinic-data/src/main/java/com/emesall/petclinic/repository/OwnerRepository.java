package com.emesall.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
	
	//method with query,no need for so long name of method
	//@Query("SELECT o FROM Owner o WHERE UPPER(o.lastName) LIKE UPPER(?1) ")
	List<Owner> findByLastNameLikeIgnoreCase(String name);
	Optional<Owner> findByUsername(String username);
	
	
	
}
