package com.ea.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ea.freelancer.domain.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
	Credentials findOneByUserName(String userName);

}
