package com.emesall.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Visit;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long> {

}
