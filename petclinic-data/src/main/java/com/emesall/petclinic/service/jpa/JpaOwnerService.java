package com.emesall.petclinic.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.repository.OwnerRepository;
import com.emesall.petclinic.service.OwnerService;

@Service
@Profile("jpa")
public class JpaOwnerService extends JpaAbstractClassService<Owner, OwnerRepository, Long>
		implements OwnerService, UserDetailsService {

	public JpaOwnerService(OwnerRepository repository) {
		super(repository);
	}

	@Override
	public List<Owner> findByLastName(String lastName) {

		return repository.findByLastNameLikeIgnoreCase(lastName);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Owner owner = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Vet" + username + "not found"));
		return owner;
	}

	@Override
	public Optional<Owner> findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public boolean checkIfOwnerExists(Owner owner) {

		if (findByUsername(owner.getUsername()).isPresent()) {
			return true;
		}

		return false;
	}

}
