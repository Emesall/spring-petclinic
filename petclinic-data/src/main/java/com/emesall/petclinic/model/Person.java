package com.emesall.petclinic.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING)
public class Person extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = -5207483840515216508L;

	@NotBlank
	@Column(name = "email")
	private String email;
	
	@NotBlank
	@Column(name = "username")
	private String username;

	@NotBlank
	@Column(name = "password")
	private String password;

	@NotBlank
	@Column(name = "first_name")
	private String firstName;

	@NotBlank
	@Column(name = "last_name")
	private String lastName;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
