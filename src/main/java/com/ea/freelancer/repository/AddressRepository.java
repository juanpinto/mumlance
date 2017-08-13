package com.ea.freelancer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ea.freelancer.domain.Address;
import com.ea.freelancer.domain.User;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	List<Address> findAllByUser(User user);

}
