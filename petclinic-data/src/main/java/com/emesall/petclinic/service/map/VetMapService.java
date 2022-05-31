package com.emesall.petclinic.service.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.exceptions.NotFoundException;
import com.emesall.petclinic.model.Vet;
import com.emesall.petclinic.service.SpecialityService;
import com.emesall.petclinic.service.VetService;

@Service
@Profile({ "default", "map" })
public class VetMapService extends AbstractClassService<Vet, Long> implements VetService {

	private final SpecialityService specialityService;

	@Autowired
	public VetMapService(SpecialityService specialityService) {
		super();
		this.specialityService = specialityService;
	}

	@Override
	public Vet save(Vet object) {
		if (object != null) {
			if (object.getSpecialities() != null) {
				object.getSpecialities().forEach(spec ->
					{
						specialityService.save(spec);

					});
			}

			return super.save(object);

		} else {
			return null;
		}
	}

	@Override
	public Page<Vet> findByLastName(String lastName,Pageable pageable) {
		//return map.values().stream().filter(vet -> vet.getLastName().equals(lastName)).toList();
		return null;
	}

	@Override
	public Vet findByEmail(String email) {
		return map.values()
				.stream()
				.filter(vet -> vet.getEmail().equals(email))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Person with " + email + " not found"));
	}

	@Override
	public boolean checkIfExists(Vet veti) {
		if (map.values().stream().filter(vet -> vet.getUsername().equals(veti.getUsername())).findFirst().isPresent()) {
			return true;
		}

		return false;
	}

}
