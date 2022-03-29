package com.emesall.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

}
