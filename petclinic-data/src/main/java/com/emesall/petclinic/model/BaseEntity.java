package com.emesall.petclinic.model;

import java.io.Serializable;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 2621076767412782986L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
