package com.emesall.petclinic.service.map;

import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Visit;
import com.emesall.petclinic.service.VisitService;

@Service
public class VisitMapService extends AbstractClassService<Visit, Long> implements VisitService {

	@Override
	public Visit save(Visit object) {
		if (object != null) {
			if (object.getPet() == null) {

				throw new RuntimeException("Invalid visit");
			}
			else return super.save(object);
		} else {
			return null;
		}
	}

}
