package com.emesall.petclinic.bootstrap;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.emesall.petclinic.model.Admin;
import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.model.PetType;
import com.emesall.petclinic.model.Speciality;
import com.emesall.petclinic.model.Vet;
import com.emesall.petclinic.model.Visit;
import com.emesall.petclinic.service.AdminService;
import com.emesall.petclinic.service.OwnerService;
import com.emesall.petclinic.service.PetTypeService;
import com.emesall.petclinic.service.SpecialityService;
import com.emesall.petclinic.service.VetService;
import com.emesall.petclinic.service.VisitService;

@Component
public class DataLoader implements CommandLineRunner {

	private final OwnerService ownerService;
	private final VetService vetService;
	private final AdminService adminService;
	private final PetTypeService petTypeService;
	private final SpecialityService specialityService;
	private final VisitService visitService;
	private final PasswordEncoder encoder;


	@Autowired
	public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
			SpecialityService specialityService, VisitService visitService, PasswordEncoder encoder,
			AdminService adminService) {
		super();
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
		this.specialityService = specialityService;
		this.visitService = visitService;
		this.encoder = encoder;
		this.adminService = adminService;
	
		
	}

	@Override
	public void run(String... args) throws Exception {

		int count = petTypeService.findAll().size();

		if (count == 0) {
			loadData();
		}
		if (adminService.findAll().size() == 0) {
			loadAdmins();
		}

	}

	private void loadData() {

		PetType dog = new PetType();
		dog.setName("Dog");
		petTypeService.save(dog);

		PetType cat = new PetType();
		cat.setName("Cat");
		petTypeService.save(cat);

		Speciality radiology = new Speciality();
		radiology.setDescription("Radiology");
		specialityService.save(radiology);

		Speciality surgery = new Speciality();
		surgery.setDescription("Surgery");
		specialityService.save(surgery);

		Speciality dentistry = new Speciality();
		dentistry.setDescription("dentistry");
		specialityService.save(dentistry);

		Owner owner1 = Owner.builder()
				.username("owner1")
				.password(encoder.encode("owner1"))
				.firstName("Michael")
				.lastName("Weston")
				.address("123 Brickerel")
				.city("Miami")
				.telephone("123232")
				.build();

		Pet mikesPet = new Pet();
		mikesPet.setPetType(dog);
		mikesPet.setBirthDate(LocalDate.now());
		mikesPet.setName("Rosco");
		owner1.addPet(mikesPet);

		Pet mikesCat = new Pet();
		mikesCat.setPetType(cat);
		mikesCat.setBirthDate(LocalDate.now());
		mikesCat.setName("Catty");
		owner1.addPet(mikesCat);

		ownerService.save(owner1);
	

		Owner owner2 = new Owner();
		owner2.setUsername("owner2");
		owner2.setPassword(encoder.encode("owner2"));
		owner2.setFirstName("Fiona");
		owner2.setLastName("Glenanne");
		owner2.setAddress("123 Brickerel");
		owner2.setCity("Miami");
		owner2.setTelephone("1231231234");

		Pet fionasCat = new Pet();
		fionasCat.setName("Just Cat");
		fionasCat.setOwner(owner2);
		fionasCat.setBirthDate(LocalDate.now());
		fionasCat.setPetType(cat);
		owner2.getPets().add(fionasCat);

		ownerService.save(owner2);
	

		Visit dogVisit = new Visit();
		dogVisit.setPet(mikesPet);
		dogVisit.setDate(LocalDate.now());
		dogVisit.setDescription("Dog problem");

		visitService.save(dogVisit);

		Visit catVisit = new Visit();
		catVisit.setPet(fionasCat);
		catVisit.setDate(LocalDate.now());
		catVisit.setDescription("Sneezy Kitty");

		visitService.save(catVisit);

		System.out.println("Loaded Owners....");

		Vet vet1 = new Vet();
		vet1.setUsername("vet1");
		vet1.setPassword(encoder.encode("vet1"));
		vet1.setFirstName("Sam");
		vet1.setLastName("Axe");
		vet1.getSpecialities().add(radiology);

		vetService.save(vet1);

		Vet vet2 = new Vet();
		vet2.setUsername("vet2");
		vet2.setPassword(encoder.encode("vet2"));
		vet2.setFirstName("Jessie");
		vet2.setLastName("Porter");
		vet2.getSpecialities().add(surgery);

		vetService.save(vet2);

		System.out.println("Loaded Vets....");
	}

	private void loadAdmins() {
		Admin admin1 = Admin.builder().username("panda").password(encoder.encode("piu")).firstName("admin1").lastName("admin1").build();
		Admin admin2 = Admin.builder().username("kot").password(encoder.encode("dziab")).firstName("admin2").lastName("admin2").build();
		adminService.save(admin1);
		adminService.save(admin2);

	}
}
