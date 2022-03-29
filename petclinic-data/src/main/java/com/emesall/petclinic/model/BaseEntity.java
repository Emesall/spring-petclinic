package com.emesall.petclinic.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 2621076767412782986L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

}
