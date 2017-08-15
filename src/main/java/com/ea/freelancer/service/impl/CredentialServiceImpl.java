package com.ea.freelancer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ea.freelancer.domain.Credentials;
import com.ea.freelancer.repository.CredentialsRepository;
import com.ea.freelancer.service.CredentialService;

@Service
@Transactional
public class CredentialServiceImpl implements CredentialService {

	@Autowired
	private CredentialsRepository credentialRepository;

	public Credentials findOneByUserName(String userName) {
		return credentialRepository.findOneByUserName(userName);
	}

	public void save(Credentials credentials) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(credentials.getPassword());
		credentials.setPassword(encodedPassword);

		credentialRepository.save(credentials);

	}

	
	public void saveCredential(Credentials credentials) {
		credentialRepository.save(credentials);
	}

}
