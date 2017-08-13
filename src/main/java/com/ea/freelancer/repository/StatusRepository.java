package com.ea.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ea.freelancer.domain.Status;
import com.ea.freelancer.domain.Status.ProjectStatus;

public interface StatusRepository extends JpaRepository<Status, Integer> {

	Status findOneByProjectStatus(ProjectStatus projectStatus);
	
}
