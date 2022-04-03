package com.emesall.petclinic.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.URI;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.emesall.petclinic.model.Pet;
import com.emesall.petclinic.service.PetService;
import com.emesall.petclinic.service.VisitService;

class VisitControllerTest {

	private static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
	private static final String REDIRECT_OWNERS_1 = "redirect:/owners/{ownerId}";

	@Mock
	PetService petService;

	@Mock
	VisitService visitService;

	@InjectMocks
	VisitController visitController;

	private MockMvc mockMvc;

	private final URI visitsUri = URI.create("/owners/1/pets/1/visits/new");

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();

		Long petId = 1L;
		Long ownerId = 1L;
		Pet pet = new Pet();
		pet.setId(petId);
		pet.setBirthDate(LocalDate.of(2018, 11, 13));
		pet.setName("name");

		when(petService.findById(anyLong())).thenReturn(pet);

	}

	@Test
	void initNewVisitForm() throws Exception {
		mockMvc.perform(get(visitsUri))
				.andExpect(status().isOk())
				.andExpect(view().name(PETS_CREATE_OR_UPDATE_VISIT_FORM));
	}

	@Test
	void processNewVisitForm() throws Exception {
		mockMvc.perform(post(visitsUri).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("date", "2018-11-11")
				.param("description", "yet another visit"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name(REDIRECT_OWNERS_1))
				.andExpect(model().attributeExists("visit"));
	}

}
