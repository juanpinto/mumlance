package com.ea.freelancer.controller;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ea.freelancer.domain.Category;
import com.ea.freelancer.domain.Employer;
import com.ea.freelancer.domain.Project;
import com.ea.freelancer.domain.Skills;
import com.ea.freelancer.domain.Skills.SkillTitle;
import com.ea.freelancer.service.CategoryService;
import com.ea.freelancer.service.ProjectService;
import com.ea.freelancer.service.SkillService;
import com.ea.freelancer.service.UserService;


@Controller
@RequestMapping(value = "/employer")
public class EmployerController {

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private SkillService skillService;

	@ModelAttribute("newProject")
	public Project getProject() {
		return new Project();
	}

	
	@RequestMapping(value = "/profile")
	public String viewProfile(Model model, Principal principal) {
		String username = principal.getName();
		Employer employer = (Employer) userService.findOneByUsername(username);
		List<Project> projects = projectService.findAllByEmployer(employer);
		employer.setProject(projects);
		model.addAttribute("currentUser", employer);
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("skills", skillService.findAll());

		return "employerProfile";
	}

	
	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	public String addProject(@Valid @ModelAttribute("newProject") Project project, BindingResult result,
			Principal principal) {

		String username = principal.getName();
		project.setEmployer((Employer) userService.findOneByUsername(username));
		if (result.hasErrors()) {
			return "employerProfile";
		}
		projectService.saveProject(project);
		return "redirect:/employer/profile.html";

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
					Skills skill = new Skills();
					try {
						Integer id = Integer.parseInt(element.toString());
						skill = skillService.getSkillById(id);
					} catch (NumberFormatException e) {
						skill = skillService.getSkillBySkillTitle(SkillTitle.valueOf(element.toString()));
					}
					return skill;
				}
				return null;
			}
		});
	}

}
