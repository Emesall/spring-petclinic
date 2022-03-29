package com.emesall.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Vet;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {

}
