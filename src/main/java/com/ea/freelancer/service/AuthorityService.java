package com.ea.freelancer.service;

import java.util.List;

import com.ea.freelancer.domain.Authority;

public interface AuthorityService {
	
	public void save(Authority authority);

	public List<Authority> findAll();

	public Authority findOneById(Integer id);

	public Authority findOneByName(String name);

}
