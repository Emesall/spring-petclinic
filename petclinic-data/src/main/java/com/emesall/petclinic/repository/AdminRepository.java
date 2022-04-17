package com.emesall.petclinic.repository;

import org.springframework.stereotype.Repository;

import com.emesall.petclinic.model.Admin;

@Repository
public interface AdminRepository extends PersonRepository<Admin> {
	

}
