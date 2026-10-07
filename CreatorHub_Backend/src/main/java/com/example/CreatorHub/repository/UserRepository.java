package com.example.CreatorHub.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CreatorHub.entity.Role;
import com.example.CreatorHub.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<User> findByRoleAndFullNameContainingIgnoreCase(Role role, String fullName);
    long countByRole(Role role);
}
