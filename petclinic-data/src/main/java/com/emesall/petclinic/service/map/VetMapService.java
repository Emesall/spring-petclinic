package com.emesall.petclinic.service.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Vet;
import com.emesall.petclinic.service.SpecialityService;
import com.emesall.petclinic.service.VetService;

@Service
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

}
