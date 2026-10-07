package com.example.CreatorHub.controller;

	import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CreatorHub.entity.Experience;
import com.example.CreatorHub.repository.ExperienceRepository;
import com.example.CreatorHub.repository.UserRepository;

	@RestController
	@RequestMapping("/api/experiences")
	public class ExperienceController extends OwnedCrudController<Experience> {
	    public ExperienceController(ExperienceRepository repo, UserRepository users) { super(repo, users); }

	    @Override
	    protected void copy(Experience f, Experience t) {
	        t.setCompany(f.getCompany());
	        t.setJobTitle(f.getJobTitle());
	        t.setStartDate(f.getStartDate());
	        t.setEndDate(f.getEndDate());
	        t.setDescription(f.getDescription());
	    }
	}

