package com.ea.freelancer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ea.freelancer.service.CategoryService;
import com.ea.freelancer.service.ProjectService;


@Controller
@RequestMapping("/rest")
public class RestURLController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProjectService projectService;

}
