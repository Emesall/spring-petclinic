package com.emesall.petclinic.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoginRegisterIntegrationTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void registerWithInvalidCSRF() throws Exception {
		mockMvc.perform(post("/register/owner").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", "owner3")
				.param("password", "owner3")
				.param("matchingPassword", "owner3")
				.param("firstName", "owner3")
				.param("lastName", "owner3")
				.with(anonymous())
				.with(csrf().useInvalidToken())).andExpect(status().isFound()).andExpect(redirectedUrl("/403"));
	}

	@Test
	public void registerWithCSRF() throws Exception {
		mockMvc.perform(post("/register/owner").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", "owner3")
				.param("password", "owner3")
				.param("matchingPassword", "owner3")
				.param("firstName", "owner3")
				.param("lastName", "owner3")
				.with(anonymous())
				.with(csrf())).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login?registered"));
	}

	@Test
	public void loginWrongCredentials() throws Exception {
		mockMvc.perform(formLogin().password("invalid")).andExpect(unauthenticated());
	}

	@Test
	public void loginSuccesful() throws Exception {
		mockMvc.perform(formLogin().user("owner1").password("owner1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(authenticated());
	}

	@Test
	public void logoutSuccessful() throws Exception {
		mockMvc.perform(logout()).andExpect(unauthenticated());
	}

}
