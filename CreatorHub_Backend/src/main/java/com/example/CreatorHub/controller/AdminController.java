package com.example.CreatorHub.controller;

	import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.CreatorHub.dto.Dtos.ProfileResponse;
import com.example.CreatorHub.entity.Role;
import com.example.CreatorHub.entity.User;
import com.example.CreatorHub.repository.MessageRepository;
import com.example.CreatorHub.repository.OwnedRepository;
import com.example.CreatorHub.repository.ProjectRepository;
import com.example.CreatorHub.repository.UserRepository;

	@RestController
	@RequestMapping("/api/admin")
	public class AdminController {

	    private final UserRepository users;
	    private final MessageRepository messages;
	    private final ProjectRepository projects;
	    private final List<OwnedRepository<?>> ownedRepos;

	    public AdminController(UserRepository users, MessageRepository messages,
	                           ProjectRepository projects, List<OwnedRepository<?>> ownedRepos) {
	        this.users = users;
	        this.messages = messages;
	        this.projects = projects;
	        this.ownedRepos = ownedRepos;
	    }

	    @GetMapping("/stats")
	    public Map<String, Long> stats() {
	        return Map.of(
	                "totalUsers", users.count(),
	                "creators", users.countByRole(Role.CREATOR),
	                "recruiters", users.countByRole(Role.RECRUITER),
	                "projects", projects.count(),
	                "messages", messages.count());
	    }

	    @GetMapping("/users")
	    public List<ProfileResponse> allUsers() {
	        return users.findAll().stream().map(u -> ProfileResponse.from(u, true)).toList();
	    }

	    @DeleteMapping("/users/{id}")
	    @Transactional
	    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
	        User u = users.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	        if (u.getRole() == Role.ADMIN) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin accounts cannot be deleted");
	        }
	        ownedRepos.forEach(r -> r.deleteByOwnerId(id));
	        messages.deleteByRecipientId(id);
	        users.delete(u);
	        return ResponseEntity.noContent().build();
	    }
	}

