package com.emesall.petclinic.service.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.service.PetService;

@Service
@Profile({"default","map"})
public class PetMapService extends AbstractClassService<Pet, Long> implements PetService{

	

}
