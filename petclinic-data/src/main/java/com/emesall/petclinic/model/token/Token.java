package com.emesall.petclinic.model.token;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import com.emesall.petclinic.model.BaseEntity;
import com.emesall.petclinic.model.Person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING)
public abstract class Token extends BaseEntity {

	private static final long serialVersionUID = 4144147332782838684L;
	private static final int EXPIRATION = 60 * 24;

	private String token;
	@OneToOne(fetch = FetchType.EAGER)
	private Person person;
	private Date expirationDate;
	
	public Token(String token) {
		super();
		this.token=token;
		expirationDate=calculateExpiryDate(EXPIRATION);
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}
}
