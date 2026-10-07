package com.example.CreatorHub.config;

	import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.CreatorHub.entity.Role;
import com.example.CreatorHub.entity.User;
import com.example.CreatorHub.repository.UserRepository;

	@Component
	public class DataSeeder implements CommandLineRunner {

	    private final UserRepository users;
	    private final PasswordEncoder encoder;

	    public DataSeeder(UserRepository users, PasswordEncoder encoder) {
	        this.users = users;
	        this.encoder = encoder;
	    }

	    @Override
	    public void run(String... args) {
	        if (!users.existsByEmail("admin@creatorhub.com")) {
	            User admin = new User();
	            admin.setUsername("admin");
	            admin.setFullName("Platform Admin");
	            admin.setEmail("admin@creatorhub.com");
	            admin.setPassword(encoder.encode("admin123"));
	            admin.setRole(Role.ADMIN);
	            users.save(admin);
	            System.out.println(">>> Default admin created: admin@creatorhub.com / admin123");
	        }
	    }
	}

