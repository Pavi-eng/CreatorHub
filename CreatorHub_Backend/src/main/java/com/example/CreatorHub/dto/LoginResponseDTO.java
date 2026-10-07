package com.example.CreatorHub.dto;

public record LoginResponseDTO(
        String token,
        Long id,
        String username,
        String fullName,
        String email,
        String role) {
}