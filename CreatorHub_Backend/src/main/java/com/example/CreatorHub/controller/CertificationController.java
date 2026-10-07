package com.example.CreatorHub.controller;

	import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CreatorHub.entity.Certification;
import com.example.CreatorHub.repository.CertificationRepository;
import com.example.CreatorHub.repository.UserRepository;

	@RestController
	@RequestMapping("/api/certifications")
	public class CertificationController extends OwnedCrudController<Certification> {
	    public CertificationController(CertificationRepository repo, UserRepository users) { super(repo, users); }

	    @Override
	    protected void copy(Certification f, Certification t) {
	        t.setTitle(f.getTitle());
	        t.setIssuer(f.getIssuer());
	        t.setIssueDate(f.getIssueDate());
	        t.setCredentialUrl(f.getCredentialUrl());
	    }
	}

