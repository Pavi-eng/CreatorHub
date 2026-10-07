package com.example.CreatorHub.controller;

	import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.CreatorHub.entity.Project;
import com.example.CreatorHub.repository.UserRepository;
import com.example.CreatorHub.repository.ProjectRepository;

	@RestController
	@RequestMapping("/api/projects")
	public class ProjectController extends OwnedCrudController<Project> {
	    public ProjectController(ProjectRepository repo, UserRepository users) { super(repo, users); }

	    @Override
	    protected void copy(Project f, Project t) {
	        t.setTitle(f.getTitle());
	        t.setDescription(f.getDescription());
	        t.setTechStack(f.getTechStack());
	        t.setProjectUrl(f.getProjectUrl());
	        t.setRepoUrl(f.getRepoUrl());
	        t.setImageUrl(f.getImageUrl());
	    }
	}

