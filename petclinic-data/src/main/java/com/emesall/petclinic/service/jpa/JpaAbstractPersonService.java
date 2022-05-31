package com.emesall.petclinic.service.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.emesall.petclinic.exceptions.NotFoundException;
import com.emesall.petclinic.model.Person;
import com.emesall.petclinic.repository.PersonRepository;
import com.emesall.petclinic.service.PersonService;

@Profile("jpa")
public abstract class JpaAbstractPersonService<T extends Person>
		extends JpaAbstractClassService<T, PersonRepository<T>, Long> implements PersonService<T>, UserDetailsService {

	public JpaAbstractPersonService(PersonRepository<T> repository) {
		super(repository);
	}

	@Override
	public Page<T> findByLastName(String lastName, Pageable pageable) {

		return repository.findByLastNameLikeIgnoreCase(lastName,pageable);
	}

	@Override
	public T findByEmail(String email) {

		return repository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Person with " + email + " not found"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		T person = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Person " + username + " not found"));
		return person;
	}

	@Override
	public boolean checkIfExists(T person) {

		if (repository.findByUsername(person.getUsername()).isPresent() ) {
			return true;
		}

		return false;
	}

}
