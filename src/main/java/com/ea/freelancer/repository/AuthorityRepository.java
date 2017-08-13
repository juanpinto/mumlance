package com.ea.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ea.freelancer.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

	Authority findOneByName(String name);

}
