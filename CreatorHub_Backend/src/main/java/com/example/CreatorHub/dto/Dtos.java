package com.example.CreatorHub.dto;

	import java.util.List;

import com.example.CreatorHub.entity.Achievement;
import com.example.CreatorHub.entity.Certification;
import com.example.CreatorHub.entity.Experience;
import com.example.CreatorHub.entity.Project;
import com.example.CreatorHub.entity.Skill;
import com.example.CreatorHub.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

	public final class Dtos {
	    private Dtos() {}

	    public record RegisterRequest(
	            @NotBlank(message = "Full name is required") String fullName,
	            @NotBlank(message = "Username is required")
	            @Pattern(regexp = "^[a-zA-Z0-9_]{3,30}$", message = "Username: 3-30 letters, numbers or _") String username,
	            @NotBlank(message = "Email is required") @Email(message = "Invalid email") String email,
	            @NotBlank(message = "Password is required")
	            @Size(min = 6, message = "Password must be at least 6 characters") String password,
	            String role) {}

	    public record LoginRequest(@NotBlank String email, @NotBlank String password) {}

	    public record AuthResponse(String token, Long id, String username, String fullName, String email, String role) {}

	    public record ProfileRequest(@NotBlank(message = "Full name is required") String fullName,
	                                 String headline, String profession, String location, String bio,
	                                 String avatarUrl, String githubUrl, String linkedinUrl, String websiteUrl) {}

	    public record ProfileResponse(Long id, String username, String fullName, String email, String role,
	                                  String headline, String profession, String location, String bio,
	                                  String avatarUrl, String githubUrl, String linkedinUrl, String websiteUrl) {
	        public static ProfileResponse from(User u, boolean includeEmail) {
	            return new ProfileResponse(u.getId(), u.getUsername(), u.getFullName(),
	                    includeEmail ? u.getEmail() : null, u.getRole().name(),
	                    u.getHeadline(), u.getProfession(), u.getLocation(), u.getBio(),
	                    u.getAvatarUrl(), u.getGithubUrl(), u.getLinkedinUrl(), u.getWebsiteUrl());
	        }
	    }

	    public record PortfolioResponse(ProfileResponse profile, List<Project> projects, List<Skill> skills,
	                                    List<Certification> certifications, List<Experience> experiences,
	                                    List<Achievement> achievements) {}

	    public record ContactRequest(@NotBlank(message = "Your name is required") String senderName,
	                                 @NotBlank @Email(message = "Invalid email") String senderEmail,
	                                 String subject,
	                                 @NotBlank(message = "Message cannot be empty") String content) {}
	}

