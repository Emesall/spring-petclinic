package com.emesall.petclinic.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pet extends BaseEntity {
	
	private String name;
	private PetType petType;
	private Owner owner;
	private LocalDate birthDate;
	

}
