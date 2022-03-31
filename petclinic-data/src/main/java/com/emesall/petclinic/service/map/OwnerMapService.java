package com.emesall.petclinic.service.map;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.service.OwnerService;
import com.emesall.petclinic.service.PetService;
import com.emesall.petclinic.service.PetTypeService;

@Service
@Profile({"default","map"})
public class OwnerMapService extends AbstractClassService<Owner, Long> implements OwnerService {

	private final PetTypeService petTypeService;
	private final PetService petService;

	@Autowired
	public OwnerMapService(PetTypeService petTypeService, PetService petService) {
		this.petTypeService = petTypeService;
		this.petService = petService;
	}

	@Override
	public Owner save(Owner object) {
		if (object != null) {
			if (object.getPets() != null) {
				object.getPets().forEach(pet ->
					{

						pet.setPetType(petTypeService.save(pet.getPetType()));

						petService.save(pet);

					});
			}

			return super.save(object);

		} else {
			return null;
		}
	}

	@Override
	public List<Owner> findByLastName(String lastName) {

		return map.values().stream().filter(owner->owner.getLastName().equals(lastName)).toList();

	}

}
