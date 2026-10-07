package com.example.CreatorHub.service;


import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.CreatorHub.dto.LoginRequestDTO;
import com.example.CreatorHub.dto.LoginResponseDTO;
import com.example.CreatorHub.dto.RegisterRequestDTO;
import com.example.CreatorHub.entity.Role;
import com.example.CreatorHub.entity.User;
import com.example.CreatorHub.repository.UserRepository;
import com.example.CreatorHub.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO register(RegisterRequestDTO request) {
        String email = request.getEmail().trim().toLowerCase();
        
        String username = request.getUsername().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        if (userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
        }

        User user = new User();
        user.setFullName(request.getFullName().trim());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("RECRUITER".equalsIgnoreCase(request.getRole()) ? Role.RECRUITER : Role.CREATOR);

        userRepository.save(user);
        return toResponse(user);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return toResponse(user);
    }

    private LoginResponseDTO toResponse(User u) {
    	
    	    String token = jwtService.generateToken(u.getEmail(), u.getRole().name());
    	    System.out.println("DEBUG => id=" + u.getId() + ", email=" + u.getEmail() + ", token=" + token);
    	    return new LoginResponseDTO(token, u.getId(), u.getUsername(),
    	            u.getFullName(), u.getEmail(), u.getRole().name());
    	}
    }

