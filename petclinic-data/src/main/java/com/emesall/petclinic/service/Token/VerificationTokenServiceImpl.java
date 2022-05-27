package com.emesall.petclinic.service.Token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Person;
import com.emesall.petclinic.model.token.VerificationToken;
import com.emesall.petclinic.repository.VerificationTokenRepository;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

	private final VerificationTokenRepository tokenRepository;
	
	
	@Autowired
	public VerificationTokenServiceImpl(VerificationTokenRepository tokenRepository) {
		super();
		this.tokenRepository = tokenRepository;
	}

	@Override
	public VerificationToken createAndSaveToken(Person person,String token) {
		VerificationToken ver_token=new VerificationToken(token);
		ver_token.setPerson(person);
		return save(ver_token);
	}

	@Override
	public VerificationToken getToken(String token) {
		return tokenRepository.getByToken(token);
	}

	@Override
	public VerificationToken save(VerificationToken verificationToken) {
		return tokenRepository.save(verificationToken);
	}

	


}
