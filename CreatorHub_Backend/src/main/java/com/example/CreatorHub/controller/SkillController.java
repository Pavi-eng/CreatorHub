package com.example.CreatorHub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CreatorHub.entity.Skill;
import com.example.CreatorHub.repository.OwnedRepository;
import com.example.CreatorHub.repository.UserRepository;

@RestController
@RequestMapping("/api/skills")
public class SkillController extends OwnedCrudController<Skill> {

    public SkillController(OwnedRepository<Skill> repo, UserRepository users) {
        super(repo, users);
    }

    @Override
    protected void copy(Skill f, Skill t) {
        t.setName(f.getName());
        t.setLevel(f.getLevel());
        t.setCategory(f.getCategory());
    }
}