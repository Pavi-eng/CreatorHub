package com.example.CreatorHub.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.CreatorHub.dto.Dtos.ContactRequest;
import com.example.CreatorHub.dto.Dtos.PortfolioResponse;
import com.example.CreatorHub.dto.Dtos.ProfileResponse;
import com.example.CreatorHub.entity.Message;
import com.example.CreatorHub.entity.Role;
import com.example.CreatorHub.entity.User;
import com.example.CreatorHub.repository.AchievementRepository;
import com.example.CreatorHub.repository.CertificationRepository;
import com.example.CreatorHub.repository.ExperienceRepository;
import com.example.CreatorHub.repository.MessageRepository;
import com.example.CreatorHub.repository.ProjectRepository;
import com.example.CreatorHub.repository.SkillRepository;
import com.example.CreatorHub.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final UserRepository users;
    private final ProjectRepository projects;
    private final SkillRepository skills;
    private final CertificationRepository certifications;
    private final ExperienceRepository experiences;
    private final AchievementRepository achievements;
    private final MessageRepository messages;

    public PublicController(UserRepository users, ProjectRepository projects, SkillRepository skills,
                            CertificationRepository certifications, ExperienceRepository experiences,
                            AchievementRepository achievements, MessageRepository messages) {
        this.users = users;
        this.projects = projects;
        this.skills = skills;
        this.certifications = certifications;
        this.experiences = experiences;
        this.achievements = achievements;
        this.messages = messages;
    }

    // ---------- Creators ----------

    @GetMapping("/creators")
    public List<ProfileResponse> searchCreators(@RequestParam(defaultValue = "") String search) {
        return users.findByRoleAndFullNameContainingIgnoreCase(Role.CREATOR, search.trim())
                .stream().map(u -> ProfileResponse.from(u, false)).toList();
    }

    @GetMapping("/creators/{username}")
    public PortfolioResponse portfolio(@PathVariable String username) {
        User u = findByRole(username, Role.CREATOR);
        Long id = u.getId();
        return new PortfolioResponse(ProfileResponse.from(u, false),
                projects.findByOwnerId(id), skills.findByOwnerId(id),
                certifications.findByOwnerId(id), experiences.findByOwnerId(id),
                achievements.findByOwnerId(id));
    }

    @PostMapping("/creators/{username}/contact")
    public ResponseEntity<Map<String, String>> contactCreator(@PathVariable String username,
                                                              @Valid @RequestBody ContactRequest r,
                                                              Authentication auth) {
        return sendMessage(findByRole(username, Role.CREATOR), r, auth);
    }

    // ---------- Recruiters ----------

    @GetMapping("/recruiters")
    public List<ProfileResponse> searchRecruiters(@RequestParam(defaultValue = "") String search) {
        return users.findByRoleAndFullNameContainingIgnoreCase(Role.RECRUITER, search.trim())
                .stream().map(u -> ProfileResponse.from(u, false)).toList();
    }

    @GetMapping("/recruiters/{username}")
    public ProfileResponse recruiterProfile(@PathVariable String username) {
        return ProfileResponse.from(findByRole(username, Role.RECRUITER), false);
    }

    @PostMapping("/recruiters/{username}/contact")
    public ResponseEntity<Map<String, String>> contactRecruiter(@PathVariable String username,
                                                                @Valid @RequestBody ContactRequest r,
                                                                Authentication auth) {
        return sendMessage(findByRole(username, Role.RECRUITER), r, auth);
    }

    // ---------- Helpers ----------

    private ResponseEntity<Map<String, String>> sendMessage(User recipient, ContactRequest r,
                                                            Authentication auth) {
        User sender = users.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please login"));

        if (sender.getId().equals(recipient.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot send a message to yourself");
        }

        Message m = new Message();
        m.setRecipient(recipient);
        m.setSenderName(sender.getFullName());      // taken from the logged-in user
        m.setSenderEmail(sender.getEmail());
        m.setSenderUsername(sender.getUsername());
        m.setSenderRole(sender.getRole().name());
        m.setSubject(r.subject());
        m.setContent(r.content());
        messages.save(m);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Message sent successfully"));
    }

    private User findByRole(String username, Role role) {
        return users.findByUsername(username.toLowerCase())
                .filter(u -> u.getRole() == role)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, role.name().toLowerCase() + " not found"));
    }
}