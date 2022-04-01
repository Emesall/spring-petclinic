package com.emesall.petclinic.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.model.PetType;
import com.emesall.petclinic.service.OwnerService;
import com.emesall.petclinic.service.PetService;
import com.emesall.petclinic.service.PetTypeService;

class PetControllerTest {

	private static final String NAME = "test";

	private static final long ID = 1L;

	@InjectMocks
	PetController petController;

	MockMvc mockMvc;

	@Mock
	PetService petService;

	@Mock
	Model model;

	@Mock
	OwnerService ownerService;

	@Mock
	PetTypeService petTypeService;

	Owner owner;

	Set<PetType> petTypes;

	Pet pet = new Pet();;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
		owner = Owner.builder().id(ID).lastName(NAME).build();
		pet.setId(ID);
		pet.setName(NAME);

		// prepare for all tests
		when(petService.save(any(Pet.class))).thenReturn(pet);
		when(ownerService.findById(anyLong())).thenReturn(owner);
		when(petTypeService.findAll()).thenReturn(petTypes);
		when(petService.findById(anyLong())).thenReturn(pet);
	}

	@Test
	void testInitCreationForm() throws Exception {

		mockMvc.perform(get("/owners/1/pets/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"))
				.andExpect(model().attributeExists("pet"));

		verifyNoInteractions(petService);
		verifyAllModelAttributes();
	}

	@Test
	void testProcessCreationForm() throws Exception {

		// then
		mockMvc.perform(post("/owners/1/pets/new"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/1"))
				.andExpect(model().attributeExists("pet"));

		verifyAllModelAttributes();
		verify(petService).save(any(Pet.class));
	}

	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/owners/1/pets/1/edit"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"))
				.andExpect(model().attributeExists("pet"));

		verify(petService).findById(anyLong());
		verifyAllModelAttributes();
	}

	@Test
	void testProcessUpdateForm() throws Exception {
		// then
		mockMvc.perform(post("/owners/1/pets/1/edit"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/1"))
				.andExpect(model().attributeExists("pet"));

		verifyAllModelAttributes();
		verify(petService).save(any(Pet.class));
	}
	
	private void verifyAllModelAttributes() {
		verify(ownerService).findById(anyLong());
		verify(petTypeService, times(1)).findAll();
	}

}
