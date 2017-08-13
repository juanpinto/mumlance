package com.ea.freelance.service;

import java.util.List;

import com.ea.freelancer.domain.Category.CategoryTitle;
import com.ea.freelancer.domain.Employer;
import com.ea.freelancer.domain.Project;
import com.ea.freelancer.domain.Skills.SkillTitle;

public interface ProjectService {
	
	public List<Project> findByTitleAndDesc(String search);

	public List<Project> findAll(Integer freelancerId);

	void saveProject(Project project);

	List<Project> findAllByEmployer(Employer employer);

	Project findById(Integer id);

	List<Project> findAllAppliedProjects(Integer freelancerId);

	List<Project> findAllNotAppliedprojects(String key, Integer freelancerId);

	List<Project> findAllByFilter(Integer freelancerId, List<SkillTitle> skillTitles, CategoryTitle categoryTitle,
			Double minBudget, Double maxBudget);

	public Project getProjectById(Integer projectId);

	public void updateProject(Project project);

}
