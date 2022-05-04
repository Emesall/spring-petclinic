package com.emesall.petclinic.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true,exclude="owner")
@ToString(exclude = "owner")
@Entity
@Table(name = "pet")
public class Pet extends BaseEntity {


	private static final long serialVersionUID = 4168386455313856953L;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "pet_type_id")
	private PetType petType;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Lob
	private byte[] image;

	@OneToMany(cascade = CascadeType.ALL ,mappedBy = "pet")
	private Set<Visit> visits = new HashSet<Visit>();

}
