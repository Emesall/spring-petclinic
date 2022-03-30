package com.emesall.petclinic.service.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.PetType;
import com.emesall.petclinic.service.PetTypeService;

@Service
@Profile({"default","map"})
public class PetTypeMapService extends AbstractClassService<PetType, Long> implements PetTypeService {

	
	

}
