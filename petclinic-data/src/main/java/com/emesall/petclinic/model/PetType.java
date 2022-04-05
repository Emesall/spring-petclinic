package com.emesall.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="pet_type")
public class PetType extends BaseEntity {

	
	private static final long serialVersionUID = -5492580399951378132L;
	
	@Column(name="name")
	private String name;

	@Override
	public String toString() {
		return this.name;
	}
}
