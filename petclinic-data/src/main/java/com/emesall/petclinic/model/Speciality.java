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

	@Column(name = "description")
	private String description;
}
