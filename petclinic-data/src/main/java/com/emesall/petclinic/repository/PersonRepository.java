package com.emesall.petclinic.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Person;

@Repository
public interface PersonRepository<T extends Person> extends JpaRepository<T, Long> {

	// method with query,no need for so long name of method
	// @Query("SELECT o FROM Person o WHERE UPPER(o.lastName) LIKE UPPER(?1) ")
	// List<T> findByLastNameLikeIgnoreCase(String name);
	Optional<T> findByUsername(String username);

	Optional<T> findByEmail(String email);

	Page<T> findByLastNameLikeIgnoreCase(String name, Pageable pageable);
}
