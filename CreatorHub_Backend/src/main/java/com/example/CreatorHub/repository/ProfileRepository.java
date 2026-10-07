package com.example.CreatorHub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CreatorHub.entity.Profile;
import com.example.CreatorHub.entity.User;

	public interface ProfileRepository extends JpaRepository<Profile, Long> {
	    Optional<Profile> findByUser(User user);
	}

