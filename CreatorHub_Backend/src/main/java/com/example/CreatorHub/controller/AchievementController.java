package com.example.CreatorHub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CreatorHub.entity.Achievement;
import com.example.CreatorHub.repository.AchievementRepository;
import com.example.CreatorHub.repository.UserRepository;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController extends OwnedCrudController<Achievement> {

    public AchievementController(AchievementRepository repo, UserRepository users) {
        super(repo, users);
    }

    @Override
    protected void copy(Achievement source, Achievement target) {
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setAchievedDate(source.getAchievedDate());
    }
}	
