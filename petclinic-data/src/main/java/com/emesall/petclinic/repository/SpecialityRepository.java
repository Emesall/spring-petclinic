package com.emesall.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Speciality;

@Repository
public interface SpecialityRepository extends CrudRepository<Speciality, Long> {

}
