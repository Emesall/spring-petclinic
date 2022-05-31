package com.emesall.petclinic.service.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.exceptions.NotFoundException;
import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.service.OwnerService;
import com.emesall.petclinic.service.PetService;
import com.emesall.petclinic.service.PetTypeService;

@Service
@Profile({ "default", "map" })
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
	public Page<Owner> findByLastName(String lastName,Pageable pageable) {

		//return map.values().stream().filter(owner -> owner.getLastName().equals(lastName)).toList();
		return null;
	}

	@Override
	public Owner findByEmail(String email) {

		return map.values()
				.stream()
				.filter(owner -> owner.getEmail().equals(email))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Person with " + email + " not found"));

	}

	@Override
	public boolean checkIfExists(Owner owner) {
		if (map.values()
				.stream()
				.filter(own -> own.getUsername().equals(owner.getUsername()))
				.findFirst()
				.isPresent()) {
			return true;
		}

		return false;
	}

}
