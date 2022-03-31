package com.emesall.petclinic.controllers;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

	@ParameterizedTest
	@ValueSource(strings = {"/owners", "/owners/index", "/owners/index.html","/owners/find"})
	void testListOwners(String urlPath) throws Exception {
		// given
		Set<Owner> owners = new HashSet<>();
		owners.add(Owner.builder().id(ID).lastName(LASTNAME).build());
		when(ownerService.findAll()).thenReturn(owners);
		// then
		mockMvc.perform(get(urlPath))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/index"))
				.andExpect(model().attributeExists("owners"))
				.andExpect(model().attribute("owners", equalToObject(owners)));
	}

	@Test
    void showOwner() throws Exception {
		//given
		Owner owner=Owner.builder().id(1l).build();
        when(ownerService.findById(anyLong())).thenReturn(owner);
        //then
        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", equalToObject(owner)));
    }
}
