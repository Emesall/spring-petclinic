package com.emesall.petclinic.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.model.Vet;
import com.emesall.petclinic.service.OwnerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OwnerControllerTest {

	private static final String LASTNAME = "lastName";

	private static final long ID = 1L;

	private static final String FIRSTNAME = "firstName";

	private static final String USERNAME = "username";

	private static final String PASSWORD = "password";
	private static final String EMAIL = "email@email.com";

	@Autowired
	WebApplicationContext context;

	MockMvc mockMvc;

	@Mock
	OwnerService ownerService;

	@Mock
	Model model;

	Owner owner;

	Vet vet;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		owner = Owner.builder()
				.id(ID)
				.username(USERNAME)
				.password(PASSWORD)
				.firstName(FIRSTNAME)
				.lastName(LASTNAME)
				.build();
		vet = Vet.builder()
				.id(ID)
				.username(USERNAME)
				.password(PASSWORD)
				.firstName(FIRSTNAME)
				.lastName(LASTNAME)
				.build();

	}

	@Test
	void showAccount() throws Exception {

		mockMvc.perform(get("/owners").with(user(owner)))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/showOwner"))
				.andExpect(model().attributeExists("owner"));
	}

	@Test
	void showAccountNoAuthorization() throws Exception {

		mockMvc.perform(get("/owners").with(user(vet))).andExpect(status().isFound()).andExpect(redirectedUrl("/403"));
	}

	@Test
	void initEditOwnerForm() throws Exception {

		mockMvc.perform(get("/owners/edit").with(user(owner)))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"))
				.andExpect(model().attributeExists("owner"));

	}

	@Test
	void processEditOwnerForm() throws Exception {
		// then
		mockMvc.perform(post("/owners/edit").with(user(owner))
				.with(csrf())
				.param("email", EMAIL)
				.param("firstName", FIRSTNAME)
				.param("lastName", LASTNAME)
				.param("username", USERNAME)
				.param("password", PASSWORD))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners"));

	}

}
