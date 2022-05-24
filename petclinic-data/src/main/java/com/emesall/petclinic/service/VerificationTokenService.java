package com.emesall.petclinic.service;

import com.emesall.petclinic.model.Owner;
import com.emesall.petclinic.model.VerificationToken;

public interface VerificationTokenService {

	VerificationToken createAndSaveVerificationToken(Owner owner,String token);
	VerificationToken getToken(String token);
	VerificationToken save (VerificationToken verificationToken);
}
