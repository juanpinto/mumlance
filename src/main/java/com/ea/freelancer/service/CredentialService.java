package com.ea.freelancer.service;

import com.ea.freelancer.domain.Credentials;

public interface CredentialService {
	
	public void save(Credentials credentials);
	public Credentials findOneByUserName(String userName);

	public void saveCredential(Credentials admin);

}
