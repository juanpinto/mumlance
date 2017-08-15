package com.ea.freelancer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ea.freelancer.domain.Status;
import com.ea.freelancer.domain.Status.ProjectStatus;
import com.ea.freelancer.repository.StatusRepository;
import com.ea.freelancer.service.StatusService;

@Service
@Transactional
public class StatusServiceImpl implements StatusService {

	@Autowired
	private StatusRepository statusRepository;
	

	public Status getStatusByProjectStatus(ProjectStatus projectStatus) {
		return statusRepository.findOneByProjectStatus(projectStatus);
	}

}
