package com.ea.freelancer.service;

import java.util.List;

import com.ea.freelancer.domain.Employer;
import com.ea.freelancer.domain.Freelancer;
import com.ea.freelancer.domain.Project;
import com.ea.freelancer.domain.User;

public interface UserService {

	public void save(User user);

	public List<User> findAll();

	public User findByEmail(String email);

	public User update(User user);

	public User findOneByUsername(String userName);

	public User findUserById(Integer id);

	public Employer findEmployerById(Integer id);

	public void removeProjectFromFreelancer(Freelancer f, Project project);

	public Freelancer findFreelancerById(Integer id);

	public void saveFreelancerInProject(Project project, Freelancer freelancer);

	Freelancer findFreelancerByUserName(String username);

}
