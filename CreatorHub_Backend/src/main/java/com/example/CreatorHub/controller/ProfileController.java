package com.example.CreatorHub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.CreatorHub.dto.Dtos.ProfileRequest;
import com.example.CreatorHub.dto.Dtos.ProfileResponse;
import com.example.CreatorHub.entity.User;
import com.example.CreatorHub.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final UserRepository users;

    public ProfileController(UserRepository users) { this.users = users; }

    @GetMapping
    public ProfileResponse get(Authentication auth) {
        return ProfileResponse.from(me(auth), true);
    }

    @PutMapping
    public ProfileResponse update(@Valid @RequestBody ProfileRequest r, Authentication auth) {
        User u = me(auth);
        u.setFullName(r.fullName().trim());
        u.setHeadline(r.headline());
        u.setProfession(r.profession());
        u.setLocation(r.location());
        u.setBio(r.bio());
        u.setAvatarUrl(r.avatarUrl());
        u.setGithubUrl(r.githubUrl());
        u.setLinkedinUrl(r.linkedinUrl());
        u.setWebsiteUrl(r.websiteUrl());
        return ProfileResponse.from(users.save(u), true);
    }

    private User me(Authentication auth) {
        return users.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }
}
