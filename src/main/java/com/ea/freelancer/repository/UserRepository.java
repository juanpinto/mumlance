package com.ea.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ea.freelancer.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findOneByEmail(String email);

	User findOneByCredentialsUserName(String userName);

}
