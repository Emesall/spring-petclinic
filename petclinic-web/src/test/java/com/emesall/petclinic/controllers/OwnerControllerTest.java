package com.emesall.petclinic.controllers;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.service.OwnerService;

class OwnerControllerTest {

	private static final String LASTNAME = "test";

	private static final long ID = 1L;

	@InjectMocks
	OwnerController ownerController;

	MockMvc mockMvc;

	@Mock
	OwnerService ownerService;

	@Mock
	Model model;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();

	}
	/*
	 * that way you can test multiple sources
	 * 
	 * @ParameterizedTest
	 * 
	 * @ValueSource(strings = {"/owners", "/owners/index",
	 * "/owners/index.html","/owners/find"})
	 */

	@Test
	void showOwner() throws Exception {
		// given
		Owner owner = Owner.builder().id(1l).build();
		when(ownerService.findById(anyLong())).thenReturn(owner);
		// then
		mockMvc.perform(get("/owners/123"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/ownerDetails"))
				.andExpect(model().attribute("owner", equalToObject(owner)));
	}

	@Test
	void findOwners() throws Exception {

		mockMvc.perform(get("/owners/find"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/findOwners"))
				.andExpect(model().attributeExists("owner"));
	}

	@Test
	void processFindFormFoundOne() throws Exception {
		// given
		List<Owner> owners = new ArrayList<>();
		owners.add(Owner.builder().id(ID).lastName(LASTNAME).build());
		when(ownerService.findByLastName(anyString())).thenReturn(owners);

		// then
		mockMvc.perform(get("/owners"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/1"));

	}

	@Test
	void processFindFormFoundMore() throws Exception {
		// given
		List<Owner> owners = new ArrayList<>();
		owners.add(Owner.builder().id(ID).lastName(LASTNAME).build());
		owners.add(Owner.builder().id(2L).lastName("test").build());
		when(ownerService.findByLastName(anyString())).thenReturn(owners);

		// then
		mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/ownersList"))
				.andExpect(model().attributeExists("selections"))
				.andExpect(model().attribute("selections", equalToObject(owners)));
	}

	@Test
	void processFindFormNotFound() throws Exception {
		// given

		when(ownerService.findByLastName(anyString())).thenReturn(Collections.emptyList());

		// then
		mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/findOwners"))
				.andExpect(model().attributeExists("owner"));
	}

}
