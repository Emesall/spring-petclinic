package com.emesall.petclinic.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.emesall.petclinic.validation.PasswordMatches;

import lombok.Data;

@Data
@PasswordMatches(field = "password", fieldMatch = "matchingPassword")
public class RegistrationForm {

	@NotBlank
	private String username;
	
	@Email(regexp = ".+@.+\\..+")
    private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String matchingPassword;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private String address;
	private String city;
	private String telephone;

	public Owner DataToOwner(PasswordEncoder passwordEncoder) {
		return Owner.builder()
				.email(email)
				.username(username)
				.password(passwordEncoder.encode(password))
				.firstName(firstName)
				.lastName(lastName)
				.address(address)
				.city(city)
				.telephone(telephone)
				.build();
	}

}
