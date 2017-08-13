package com.ea.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ea.freelancer.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	


}
