package com.emesall.petclinic.service.password;

import java.util.Locale;

import com.emesall.petclinic.model.Person;

public interface ChangePasswordService {

	void sendEmail(Person person, String contextPath, Locale locale);
	
}
