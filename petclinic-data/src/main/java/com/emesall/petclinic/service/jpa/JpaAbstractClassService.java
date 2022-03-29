package com.emesall.petclinic.service.jpa;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.emesall.petclinic.model.BaseEntity;
import com.emesall.petclinic.service.CrudService;

public class JpaAbstractClassService<T extends BaseEntity, R extends JpaRepository<T, ID>,ID> implements CrudService<T, ID>{

	
	protected R repository;
	
	@Autowired
	public JpaAbstractClassService(R repository) {
		super();
		this.repository = repository;
	}

	public T findById(ID id) {
		return repository.findById(id).orElseThrow(()->new RuntimeException("Object not found"));
	}

	public T save(T object) {
		return repository.save(object);
	}

	public Set<T> findAll(){
		return repository.findAll().stream().collect(Collectors.toSet());
	}

	public void delete(T object) {
		repository.delete(object);
	}

	public void deleteById(ID id) {
		repository.deleteById(id);
	}
	
	
}
