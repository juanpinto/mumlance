package com.ea.freelancer.controller;

import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.freelancer.domain.Freelancer;
import com.ea.freelancer.domain.Project;
import com.ea.freelancer.domain.Status;
import com.ea.freelancer.domain.Status.ProjectStatus;
import com.ea.freelancer.domain.User;
import com.ea.freelancer.service.ProjectService;
import com.ea.freelancer.service.StatusService;
import com.ea.freelancer.service.UserService;
import com.ea.freelancer.service.email.EmailService;


@Controller
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;

	@Autowired
	private StatusService statusService;

	
	@RequestMapping("/forInterview")
	public String sendInterviewEmail(@RequestParam("f_id") Integer freelancerId,
			@RequestParam("p_id") Integer projectId, Model model) {
		try {
			Project project = projectService.getProjectById(projectId);
			User freelancer = userService.findUserById(freelancerId);
			emailService.sendInterviewInvitation((Freelancer) freelancer, project, new Locale("en"));

			Status status = statusService.getStatusByProjectStatus(ProjectStatus.CALL_FOR_INTERVIEW);
			project.setStatus(status);

			projectService.updateProject(project);

			model.addAttribute("error", false);
			model.addAttribute("f", freelancer);
		} catch (MessagingException e) {
			model.addAttribute("error", true);
		}
		return "redirect:/employer/profile.html";
	}


	@RequestMapping("/forHiring")
	public String sendHiringEmail(@RequestParam("f_id") Integer freelancerId, @RequestParam("p_id") Integer projectId,
			Model model) {
		try {
			Project project = projectService.getProjectById(projectId);
			User freelancer = userService.findUserById(freelancerId);
			emailService.sendHiringEmail((Freelancer) freelancer, project, new Locale("en"));

			Status status = statusService.getStatusByProjectStatus(ProjectStatus.ACCEPTED);
			project.setStatus(status);

			projectService.updateProject(project);

			model.addAttribute("error", false);
			model.addAttribute("f", freelancer);
		} catch (MessagingException e) {
			model.addAttribute("error", true);
		}
		return "redirect:/employer/profile.html";
	}

}
