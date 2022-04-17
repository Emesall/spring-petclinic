package com.emesall.petclinic.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Owner extends Person {

	private static final long serialVersionUID = 4998824591446951188L;

	@Column(name = "address")
	private String address;
	@Column(name = "city")
	private String city;

	@Column(name = "telephone")
	private String telephone;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private final Set<Pet> pets = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_OWNER"));
	}

	public void addPet(Pet pet) {
		pet.setOwner(this);
		pets.add(pet);
	}

	public boolean checkIfPresent(Pet pet) {
		if (pets.stream().filter(p -> p.getName().equals(pet.getName())).findFirst().isPresent()) {
			return true;
		}
		return false;
	}

}
