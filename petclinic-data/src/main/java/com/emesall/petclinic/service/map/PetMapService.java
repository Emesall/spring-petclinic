package com.emesall.petclinic.service.map;

import java.io.IOException;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.service.PetService;

@Service
@Profile({ "default", "map" })
public class PetMapService extends AbstractClassService<Pet, Long> implements PetService {

	public byte[] getImage(Long petId) {
		Pet pet= map.get(petId);
		return pet.getImage();
	}
	
	@Override
	public void saveImage(Long petId, MultipartFile file) {

		try {
			byte[] image = file.getBytes();
			Pet pet= map.get(petId);
			pet.setImage(image);

			
		} catch (IOException exception) {
			
			exception.printStackTrace();
		}

	}

}
