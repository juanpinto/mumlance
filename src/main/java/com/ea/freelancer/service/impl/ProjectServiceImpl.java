package com.ea.freelancer.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ea.freelancer.domain.Category.CategoryTitle;
import com.ea.freelancer.domain.Employer;
import com.ea.freelancer.domain.Freelancer;
import com.ea.freelancer.domain.Project;
import com.ea.freelancer.domain.Skills.SkillTitle;
import com.ea.freelancer.domain.Status.ProjectStatus;
import com.ea.freelancer.repository.FreelancerRepository;
import com.ea.freelancer.repository.ProjectRepository;
import com.ea.freelancer.repository.StatusRepository;
import com.ea.freelancer.service.ProjectService;
import com.ea.freelancer.validation.aspect.ServiceValidation;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private FreelancerRepository freelancerRepository;

	@Autowired
	private StatusRepository statusRepository;

	
	@ServiceValidation
	public void saveProject(Project project) {
		project.setStatus(statusRepository.findOneByProjectStatus(ProjectStatus.PENDING));

		projectRepository.save(project);
	}

	public List<Project> findAllByEmployer(Employer employer) {
		List<Project> projects = projectRepository.findAllByEmployer(employer);

		for (Project project : projects) {
			project.setFreelancers(freelancerRepository.findAllByProjectsId(project.getId()));
			for (Freelancer f : project.getFreelancers()) {
				f.setProjects(projectRepository.findAllByFreelancers(Arrays.asList(f)));
			}
		}

		return projects;
	}


	public List<Project> findAll(Integer freelancerId) {
		List<Project> projects = new ArrayList<Project>();
		List<Integer> projectIds = projectRepository.findAllNotAppliedProjects(freelancerId);
		if(projectIds==null || projectIds.isEmpty() ){
			System.out.println("null return --> findall");
			return projects;
		}
		return projectRepository.findAllByProjectId(projectIds);
	}


	public Project findById(Integer id) {
		// TODO Auto-generated method stub
		return projectRepository.findOne(id);
	}


	public List<Project> findAllAppliedProjects(Integer freelancerId) {
		List<Integer> projectId = projectRepository.findAllProjectIdByFreelancer(freelancerId);
		List<Project> projects = new ArrayList<Project>();
		if(projectId==null || projectId.isEmpty() ){

			return projects;
		}
		
		for (Integer pId : projectId) {
			
			projects.add(projectRepository.findById(pId));
			
		}
		return projects;
	}


	public List<Project> findByTitleAndDesc(String search) {
		// TODO Auto-generated method stub
		return projectRepository.findByDescAndTitle(search);
	}


	public List<Project> findAllNotAppliedprojects(String key, Integer freelancerId) {
		
		List<Integer> ids = projectRepository.findAllNotAppliedProjects(freelancerId);
		List<Project> projects = new ArrayList<Project>();
		if(ids==null || ids.isEmpty() ){
			System.out.println("null return --> findall");
			return projects;
		}
		return projectRepository.findByDescAndTitleByNotApplied(ids, key);
	}


	public List<Project> findAllByFilter(Integer freelancerId, List<SkillTitle> skillTitles,
			CategoryTitle categoryTitle, Double minBudget, Double maxBudget) {
		List<Integer> ids = projectRepository.findAllNotAppliedProjects(freelancerId);
		List<Project> projects = new ArrayList<Project>();
		if(ids==null || ids.isEmpty() ){
			System.out.println("null return --> findall");
			return projects;
		}
		return projectRepository.findAllByFilter(ids, skillTitles, categoryTitle, minBudget, maxBudget);
	}


	public Project getProjectById(Integer projectId) {
		Project project = projectRepository.findOne(projectId);
		project.setFreelancers(freelancerRepository.findAllByProjectsId(projectId));
		return project;
	}


	public void updateProject(Project project) {
		projectRepository.save(project);
	}

}
