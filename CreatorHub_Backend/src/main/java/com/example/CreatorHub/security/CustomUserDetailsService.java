package com.example.CreatorHub.security;

	import java.util.List;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.CreatorHub.repository.UserRepository;

	@Service
	public class CustomUserDetailsService implements UserDetailsService {
	    private final UserRepository users;

	    public CustomUserDetailsService(UserRepository users) { this.users = users; }

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        com.example.CreatorHub.entity.User u = users.findByEmail(email.toLowerCase())
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	        return new org.springframework.security.core.userdetails.User(
	                u.getEmail(), u.getPassword(),
	                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name())));
	    }
	}

