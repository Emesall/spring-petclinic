package com.emesall.petclinic.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Visit extends BaseEntity {

	private LocalDate date;
	private String description;
	private Pet pet;
}
