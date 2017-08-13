package com.ea.freelancer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ea.freelancer.domain.Freelancer;

public interface FreelancerRepository extends JpaRepository<Freelancer, Integer> {

	List<Freelancer> findAllByProjectsId(Integer id);
	
}
