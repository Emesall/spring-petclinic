package com.emesall.petclinic.service.jpa;

import java.io.IOException;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emesall.petclinic.exceptions.NotFoundException;
import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.repository.PetRepository;
import com.emesall.petclinic.service.PetService;


@Service
@Profile("jpa")
public class JpaPetService extends JpaAbstractClassService<Pet, PetRepository, Long> implements PetService {

	
	public JpaPetService(PetRepository repository) {
		super(repository);
		
	}
	
	@Override
	public byte[] getImage(Long petId) {
		
		Pet pet=repository.findById(petId).orElseThrow(()-> new NotFoundException("Pet not found"));
		return pet.getImage();
	}
	
	@Override
	public void saveImage(Long petId, MultipartFile file) {

		try {
			byte[] image = file.getBytes();
			Pet pet= repository.findById(petId).orElseThrow(()->new RuntimeException("No pet found"));
			pet.setImage(image);
			repository.save(pet);
			
		} catch (IOException exception) {
			
			exception.printStackTrace();
		}

	}

}
