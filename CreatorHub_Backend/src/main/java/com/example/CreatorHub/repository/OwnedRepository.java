package com.example.CreatorHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.example.CreatorHub.entity.OwnedEntity;

@NoRepositoryBean
public interface OwnedRepository<T extends OwnedEntity> extends JpaRepository<T, Long> {
    List<T> findByOwnerId(Long ownerId);
        void deleteByOwnerId(Long ownerId);
    }
