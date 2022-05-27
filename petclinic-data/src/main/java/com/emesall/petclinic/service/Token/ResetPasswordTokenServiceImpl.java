package com.emesall.petclinic.service.Token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emesall.petclinic.model.Person;
import com.emesall.petclinic.model.token.ResetPasswordToken;
import com.emesall.petclinic.repository.ResetPasswordTokenRepository;

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

	private final ResetPasswordTokenRepository tokenRepository;
	
	
	@Autowired
	public ResetPasswordTokenServiceImpl(ResetPasswordTokenRepository tokenRepository) {
		super();
		this.tokenRepository = tokenRepository;
	}

	@Override
	public ResetPasswordToken createAndSaveToken(Person person,String token) {
		ResetPasswordToken ver_token=new ResetPasswordToken(token);
		ver_token.setPerson(person);
		return save(ver_token);
	}

	@Override
	public ResetPasswordToken getToken(String token) {
		return tokenRepository.getByToken(token);
	}

	@Override
	public ResetPasswordToken save(ResetPasswordToken resetPasswordToken) {
		return tokenRepository.save(resetPasswordToken);
	}

	


}
