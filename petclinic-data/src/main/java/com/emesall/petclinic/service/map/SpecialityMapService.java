package com.emesall.petclinic.service.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Speciality;
import com.emesall.petclinic.service.SpecialityService;

@Service
@Profile({"default","map"})
public class SpecialityMapService extends AbstractClassService<Speciality, Long> implements SpecialityService {

	


	
}
