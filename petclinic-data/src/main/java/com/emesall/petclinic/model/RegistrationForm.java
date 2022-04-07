package com.emesall.petclinic.model;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class RegistrationForm {

	private String username;
	private String password;
	private String matchingPassword;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String telephone;

	public Owner DataToOwner(PasswordEncoder passwordEncoder) {
		return Owner.builder()
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
