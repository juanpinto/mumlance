package com.ea.freelancer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ea.freelancer.domain.Skills;
import com.ea.freelancer.domain.Skills.SkillTitle;
import com.ea.freelancer.repository.SkillsRepository;
import com.ea.freelancer.service.SkillService;

@Service
@Transactional
public class SkillServiceImpl implements SkillService {

	@Autowired
	private SkillsRepository skillsRepository;

	public List<Skills> findAll() {
		return skillsRepository.findAll();
	}


	public Skills getSkillById(Integer id) {
		return skillsRepository.findOne(id);
	}


	public Skills getSkillBySkillTitle(SkillTitle skillTitle) {
		return skillsRepository.findOneBySkillTitle(skillTitle);
	}

}
