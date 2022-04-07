package com.emesall.petclinic.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vet")
public class Vet extends Person {

	
	private static final long serialVersionUID = -3791045289170168284L;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_VET"));
	}
	
	@ManyToMany
	@JoinTable(name = "vet_speciality", joinColumns = @JoinColumn(name = "vet_id"), inverseJoinColumns = @JoinColumn(name = "speciality_id"))
	private Set<Speciality> specialities = new HashSet<>();
}
