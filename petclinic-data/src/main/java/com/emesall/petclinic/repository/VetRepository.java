package com.emesall.petclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Vet;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {

	Optional<Vet> findByUsername(String username);
}
