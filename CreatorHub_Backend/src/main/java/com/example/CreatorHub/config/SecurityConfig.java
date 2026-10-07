package com.example.CreatorHub.config;

	import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.CreatorHub.security.JwtAuthenticationFilter;

	@Configuration
	public class SecurityConfig {
	    private final JwtAuthenticationFilter jwtAuthFilter;

	    @Value("${app.cors.allowed-origin}")
	    private String allowedOrigin;

	    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) { this.jwtAuthFilter = jwtAuthFilter; }

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable())
	            .cors(Customizer.withDefaults())
	            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .authorizeHttpRequests(auth -> auth
	            	.requestMatchers(HttpMethod.POST,
	            		        "/api/public/creators/*/contact",
	            		        "/api/public/recruiters/*/contact").authenticated()
	            	.requestMatchers("/api/auth/**", "/api/public/**").permitAll()	
	                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	                .requestMatchers("/error").permitAll()
	                .requestMatchers(HttpMethod.POST, "/api/public/creators/*/contact").authenticated()
	                .requestMatchers("/api/auth/**", "/api/public/**").permitAll()
	                .requestMatchers("/api/admin/**").hasRole("ADMIN")
	                .anyRequest().authenticated())
	            .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) -> {
	                res.setStatus(401);
	                res.setContentType("application/json");
	                res.getWriter().write("{\"message\":\"Unauthorized - please log in\"}");
	            }))
	            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	        return http.build();
	    }

	    @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration cfg = new CorsConfiguration();
	        cfg.setAllowedOrigins(List.of(allowedOrigin));
	        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        cfg.setAllowedHeaders(List.of("*"));
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", cfg);
	        return source;
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() { 
	    	return new BCryptPasswordEncoder();
	    	}

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	}

