package com.emesall.petclinic.service.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Admin;
import com.emesall.petclinic.repository.AdminRepository;
import com.emesall.petclinic.service.AdminService;

@Service
@Profile("jpa")
public class JpaAdminService extends JpaAbstractPersonService<Admin> implements AdminService {

	public JpaAdminService(AdminRepository repository) {
		super(repository);
	}
	
}
