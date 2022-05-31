package com.emesall.petclinic.service.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.repository.OwnerRepository;

class JpaOwnerServiceTest {

	private static final String LASTNAME = "test";
	private static final long _ID = 1L;

	@InjectMocks
	JpaOwnerService jpaOwnerService;
	@Mock
	OwnerRepository ownerRepository;
	Owner owner;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		owner = Owner.builder().id(_ID).lastName(LASTNAME).build();
	}

	@Test
	void testFindOneByLastName() {
		
		// given
		Pageable pageable= PageRequest.of(0, 5);
		List<Owner> owners=new ArrayList<>();
		owners.add(owner);
		Page<Owner> page=new PageImpl<>(owners,pageable , owners.size());

		when(ownerRepository.findByLastNameLikeIgnoreCase(anyString(),any(Pageable.class))).thenReturn(page);
		// when
		Page<Owner> foundOwners = jpaOwnerService.findByLastName(LASTNAME,pageable);
		// then
		assertNotNull(foundOwners);
		verify(ownerRepository, times(1)).findByLastNameLikeIgnoreCase(anyString(),any(Pageable.class));
		assertEquals(_ID, foundOwners.getContent().get(0).getId());
		assertEquals(LASTNAME, foundOwners.getContent().get(0).getLastName());
		assertEquals(1, foundOwners.getContent().size());

	}
	
	@Test
	void testFindMoreByLastName() {
		
		// given
		Pageable pageable= PageRequest.of(0, 5);
		List<Owner> owners=new ArrayList<>();
		owners.add(owner);
		owners.add(Owner.builder().id(2L).lastName("test2222").build());
		Page<Owner> page=new PageImpl<>(owners,pageable , owners.size());
		when(ownerRepository.findByLastNameLikeIgnoreCase(anyString(),any(Pageable.class))).thenReturn(page);
		// when
		Page<Owner> foundOwners = jpaOwnerService.findByLastName(LASTNAME,pageable);
		// then
		assertNotNull(foundOwners);
		verify(ownerRepository, times(1)).findByLastNameLikeIgnoreCase(anyString(),any(Pageable.class));
		assertEquals(2, foundOwners.getContent().size());

	}

	@Test
	void testFindById() {
		// given
		when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
		// when
		Owner foundOwner = jpaOwnerService.findById(_ID);
		// then
		assertNotNull(foundOwner);
		verify(ownerRepository, times(1)).findById(anyLong());
		assertEquals(_ID, foundOwner.getId());
		assertEquals(LASTNAME, foundOwner.getLastName());
	}

	@Test
	void testSave() {
		// given
		when(ownerRepository.save(any(Owner.class))).thenReturn(owner);
		// when
		Owner foundOwner = jpaOwnerService.save(owner);
		// then
		assertNotNull(foundOwner);
		verify(ownerRepository, times(1)).save(any(Owner.class));
		assertEquals(_ID, foundOwner.getId());
		assertEquals(LASTNAME, foundOwner.getLastName());
	}

	@Test
	void testFindAll() {
		// given
		List<Owner> owners = new ArrayList<>();
		owners.add(owner);

		when(ownerRepository.findAll()).thenReturn(owners);
		// when
		Set<Owner> foundOwners = jpaOwnerService.findAll();
		// then
		assertNotNull(foundOwners);
		verify(ownerRepository, times(1)).findAll();
		assertEquals(1, foundOwners.size());
	}

	@Test
	void testDelete() {

		// when
		jpaOwnerService.delete(owner);
		// then
		verify(ownerRepository, times(1)).delete(any(Owner.class));
		;
	}

	@Test
	void testDeleteById() {
		// when
		jpaOwnerService.deleteById(_ID);
		// then
		verify(ownerRepository, times(1)).deleteById(anyLong());
		;
	}

}
