package com.emesall.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "speciality")
public class Speciality extends BaseEntity {


	
	private static final long serialVersionUID = -4427879611674083945L;
	
	@Column(name = "description")
	private String description;
}
