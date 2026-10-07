package com.example.CreatorHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CreatorHub.entity.Profile;
import com.example.CreatorHub.entity.User;
import com.example.CreatorHub.repository.ProfileRepository;
import com.example.CreatorHub.repository.UserRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;


    public Profile createOrUpdateProfile(
            String email,
            Profile profileData) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        Profile profile = profileRepository
                .findByUser(user)
                .orElse(new Profile());

        profile.setUser(user);

        profile.setHeadline(
                profileData.getHeadline()
        );

        profile.setAbout(
                profileData.getAbout()
        );

        profile.setPhone(
                profileData.getPhone()
        );

        profile.setLocation(
                profileData.getLocation()
        );

        profile.setGithub(
                profileData.getGithub()
        );

        profile.setLinkedin(
                profileData.getLinkedin()
        );

        return profileRepository.save(profile);
    }


    public Profile getMyProfile(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        return profileRepository
                .findByUser(user)
                .orElse(null);
    }
}
