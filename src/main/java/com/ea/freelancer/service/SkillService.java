package com.ea.freelancer.service;

import java.util.List;

import com.ea.freelancer.domain.Skills;
import com.ea.freelancer.domain.Skills.SkillTitle;

public interface SkillService {

	public List<Skills> findAll();

	public Skills getSkillById(Integer id);

	public Skills getSkillBySkillTitle(SkillTitle valueOf);
	
}
