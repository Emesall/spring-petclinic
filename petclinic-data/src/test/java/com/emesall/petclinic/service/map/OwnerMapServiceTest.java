package com.emesall.petclinic.service.map;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.service.PetService;
import com.emesall.petclinic.service.PetTypeService;

class OwnerMapServiceTest {

	private static final String LASTNAME = "test";
	private static final long _ID = 1L;

	OwnerMapService mapService;
	@Mock
	PetTypeService petTypeService;
	@Mock
	PetService petService;

	@BeforeEach
	void setUp() throws Exception {
		mapService = new OwnerMapService(petTypeService, petService);
		mapService.map.put(1L, Owner.builder().id(_ID).lastName(LASTNAME).build());

	}

	@Test
	void testSaveNewOwner() {
		// given
		Owner owner = Owner.builder().id(2L).lastName("test2").build();
		// when
		Owner savedOwner = mapService.save(owner);
		// then
		assertNotNull(savedOwner);
		assertEquals(2L, savedOwner.getId());
		assertEquals("test2", savedOwner.getLastName());
		assertEquals(2, mapService.map.size());
	}

	@Test
	void testSaveExistingOwner() {
		// given
		Owner owner = Owner.builder().id(_ID).lastName("test2").build();
		// when
		Owner savedOwner = mapService.save(owner);
		// then
		assertNotNull(savedOwner);
		assertEquals(1L, savedOwner.getId());
		assertEquals("test2", savedOwner.getLastName());
		assertEquals(1, mapService.map.size());
	}

	@Test
	void testFindByLastName() {
		// when
		List<Owner> foundOwner = mapService.findByLastName(LASTNAME);
		// then
		assertNotNull(foundOwner);
		assertEquals(1L, foundOwner.get(0).getId());
		assertEquals(LASTNAME, foundOwner.get(0).getLastName());
		assertEquals(1, foundOwner.size());

	}

	@Test
	void testNotFoundByLastName() {
		// when
		List<Owner> foundOwner = mapService.findByLastName("dsds");
		// then
		assertThat(foundOwner.isEmpty());


	}

	@Test
	void testFindAll() {
		// when
		Set<Owner> owners = mapService.findAll();
		// then
		assertNotNull(owners);
		assertEquals(1, owners.size());
	}

	@Test
	void testFindById() {
		// when
		Owner foundOwner = mapService.findById(1l);
		// then
		assertNotNull(foundOwner);
		assertEquals(1L, foundOwner.getId());
		assertEquals(LASTNAME, foundOwner.getLastName());
	}

	@Test
	void testNotFoundById() {
		// when
		Owner foundOwner = mapService.findById(325L);
		// then
		assertNull(foundOwner);
	}

	@Test
	void testDeleteById() {
		// when
		mapService.deleteById(1L);
		// then
		assertEquals(0, mapService.map.size());
	}

	@Test
	void testDelete() {
		// given
		Owner owner = Owner.builder().id(_ID).lastName("test2").build();
		// when
		mapService.delete(owner);
		// then
		assertEquals(0, mapService.map.size());
	}

}
