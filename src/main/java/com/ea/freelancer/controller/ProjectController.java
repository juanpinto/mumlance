package com.ea.freelancer.controller;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.freelancer.domain.Category;
import com.ea.freelancer.domain.Category.CategoryTitle;
import com.ea.freelancer.domain.Freelancer;
import com.ea.freelancer.domain.Project;
import com.ea.freelancer.domain.Skills;
import com.ea.freelancer.domain.Skills.SkillTitle;
import com.ea.freelancer.domain.Status;
import com.ea.freelancer.domain.Status.ProjectStatus;
import com.ea.freelancer.repository.EmployerRepository;
import com.ea.freelancer.repository.ProjectRepository;
import com.ea.freelancer.sender.MessageSender;
import com.ea.freelancer.service.CategoryService;
import com.ea.freelancer.service.ProjectService;
import com.ea.freelancer.service.SkillService;
import com.ea.freelancer.service.StatusService;
import com.ea.freelancer.service.UserService;


@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	MessageSender messageSender;

	@Autowired
	ProjectService projectService;
	@Autowired
	CategoryService categoryService;

	@Autowired
	SkillService skillService;

	@Autowired
	UserService userService;
	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	EmployerRepository employerRepository;

	@ModelAttribute("userStory")
	public Project getProject() {
		return new Project();
	}

	@Autowired
	private StatusService statusService;


	@RequestMapping("/hireFreelancer")
	public String hireFreelancer(@RequestParam("f_id") Integer freelancerId, @RequestParam("p_id") Integer projectId) {

		Project project = projectService.getProjectById(projectId);
		Freelancer freelancer = (Freelancer) userService.findUserById(freelancerId);

		Status status = statusService.getStatusByProjectStatus(ProjectStatus.ACCEPTED);

		project.setStatus(status);

		for (Freelancer f : project.getFreelancers()) {
			if (f.getId() != freelancerId) {
				userService.removeProjectFromFreelancer(f, project);
			}
		}

		project.getFreelancers().clear();

		project.getFreelancers().add(freelancer);

		projectService.updateProject(project);

		return "redirect:/email/forHiring.html?f_id=" + freelancerId + "&&p_id=" + projectId;
	}


	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String listProjects(Model model, Principal principal) {
		Freelancer freelancer = userService.findFreelancerByUserName(principal.getName());
		model.addAttribute("category", categoryService.findAll());
		model.addAttribute("skill", skillService.findAll());
		model.addAttribute("listProject", projectService.findAll(freelancer.getId()));
		return "project_read";
	}


	@RequestMapping(value = "/freelancer_project", method = RequestMethod.GET)
	public String freelancerProject(Model model, Principal principal) {
		Freelancer freelancer = userService.findFreelancerByUserName(principal.getName());
		model.addAttribute("listProject", projectService.findAllAppliedProjects(freelancer.getId()));
		return "freelancer_project";
	}

	@ModelAttribute("projectSearch")
	public ProjectSearchDTO projectSearchDetails() {
		return new ProjectSearchDTO();
	}


	@RequestMapping(value = "/applyProject", method = RequestMethod.GET)
	public String applyProject(@RequestParam Integer id, Principal principal) {

		Project project = projectService.findById(id);
		Freelancer freelancer = userService.findFreelancerByUserName(principal.getName());
		ProjectApplyDTO projectApplyDTO = new ProjectApplyDTO();
		projectApplyDTO.setFreelancer(freelancer);
		projectApplyDTO.setProject(project);
		messageSender.sendMessage(projectApplyDTO);

		return "redirect:freelancer_project.html";
	}


	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchProjects(Model model, @RequestParam String search, Principal principal) {
		Freelancer freelancer = userService.findFreelancerByUserName(principal.getName());

		model.addAttribute("category", categoryService.findAll());
		model.addAttribute("skill", skillService.findAll());
		model.addAttribute("listProject", projectService.findAllNotAppliedprojects(search, freelancer.getId()));
		return "project_read";
	}


	@RequestMapping(value = "/filterSearch", method = RequestMethod.POST)
	public String filterSearch(Model model,
			Principal principal) {
		Freelancer freelancer = userService.findFreelancerByUserName(principal.getName());

		CategoryTitle categoryTitle = projectSearchDTO.getCategory().getCategoryTitle();
		List<SkillTitle> skills = new ArrayList<>();

		for (Skills s : projectSearchDTO.getSkills()) {
			skills.add(s.getSkillTitle());
		}
		Double maxBudget = projectSearchDTO.getMaxBudget();

		model.addAttribute("category", categoryService.findAll());
		model.addAttribute("skill", skillService.findAll());

		model.addAttribute("listProject",
				projectService.findAllByFilter(freelancer.getId(), skills, categoryTitle, 1.0, maxBudget));
		return "project_read";
	}


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Category.class, new CategoryEditor(categoryService));
	}

	public class CategoryEditor extends PropertyEditorSupport {

		private final CategoryService categoryService;

		public CategoryEditor(CategoryService categoryService) {
			this.categoryService = categoryService;
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			try {
				Category category = categoryService.getCategoryById(Integer.parseInt(text));
				setValue(category);
			} catch (NumberFormatException e) {
				setValue(null);
			}

		}
	}


	@InitBinder
	public void skillsBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(List.class, "skills", new CustomCollectionEditor(List.class) {

			protected Object convertElement(Object element) {
				if (element != null) {
					Integer id = Integer.parseInt(element.toString());
					Skills skill = skillService.getSkillById(id);
					return skill;
				}
				return null;
			}
		});
	}

}
