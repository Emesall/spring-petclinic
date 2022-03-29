package com.emesall.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Speciality;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

}
