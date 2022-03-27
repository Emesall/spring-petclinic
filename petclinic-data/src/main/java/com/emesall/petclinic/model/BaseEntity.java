package com.emesall.petclinic.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 2621076767412782986L;
	
	private Long id;

	
}
