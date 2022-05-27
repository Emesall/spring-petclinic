package com.emesall.petclinic.model;

import javax.validation.constraints.NotBlank;

import com.emesall.petclinic.validation.PasswordMatches;

import lombok.Data;

@Data
@PasswordMatches(field = "password", fieldMatch = "matchingPassword")
public class ChangePasswordForm {

	@NotBlank
	private String password;

	@NotBlank
	private String matchingPassword;
	
	private String token;
	
	
}
