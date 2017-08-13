package com.ea.freelancer.service;

import java.util.List;

import com.ea.freelancer.domain.Category;

public interface CategoryService {

	public List<Category> findAll();

	public Category getCategoryById(int id);
	
}
