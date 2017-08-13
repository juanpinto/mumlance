package com.ea.freelance.service;

import com.ea.freelancer.domain.Status;
import com.ea.freelancer.domain.Status.ProjectStatus;

public interface StatusService {

	public Status getStatusByProjectStatus(ProjectStatus projectStatus);
	
}
