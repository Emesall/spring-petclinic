package com.emesall.petclinic.service.Token;

import com.emesall.petclinic.model.Person;
import com.emesall.petclinic.model.token.Token;

public interface TokenService<T extends Token> {

	T createAndSaveToken(Person person,String token);
	T getToken(String token);
	T save (T token);
}
