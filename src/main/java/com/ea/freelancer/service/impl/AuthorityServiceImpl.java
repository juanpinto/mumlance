package com.ea.freelancer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ea.freelancer.domain.Authority;
import com.ea.freelancer.repository.AuthorityRepository;
import com.ea.freelancer.service.AuthorityService;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	
	public void save(Authority authority) {
		authorityRepository.save(authority);
	}


	public List<Authority> findAll() {
		return authorityRepository.findAll();
	}


	public Authority findOneById(Integer id) {
		return authorityRepository.findOne(id);
	}

	public Authority findOneByName(String name) {
		return authorityRepository.findOneByName(name);
	}

}
