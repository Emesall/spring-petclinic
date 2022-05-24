package com.emesall.petclinic.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.emesall.petclinic.model.Person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7074134939627530642L;

	private String appUrl;
	private Locale locale;
	private Person person;

	public OnRegistrationCompleteEvent(Person person, Locale locale, String appUrl) {
		super(person);

		this.person = person;
		this.locale = locale;
		this.appUrl = appUrl;
	}

}
