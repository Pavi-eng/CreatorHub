package com.example.CreatorHub.exeception;

	import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

	@RestControllerAdvice
	public class GlobalExceptionHandler {

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> validation(MethodArgumentNotValidException ex) {
	        String msg = ex.getBindingResult().getFieldErrors().stream()
	                .findFirst().map(e -> e.getDefaultMessage()).orElse("Validation failed");
	        return ResponseEntity.badRequest().body(Map.of("message", msg));
	    }

	    @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<Map<String, String>> status(ResponseStatusException ex) {
	        return ResponseEntity.status(ex.getStatusCode()).body(Map.of("message", String.valueOf(ex.getReason())));
	    }

	    @ExceptionHandler(BadCredentialsException.class)
	    public ResponseEntity<Map<String, String>> badCredentials(BadCredentialsException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid email or password"));
	    }

	    @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<Map<String, String>> unreadable(HttpMessageNotReadableException ex) {
	        return ResponseEntity.badRequest().body(Map.of("message", "Invalid request body (dates must be yyyy-MM-dd)"));
	    }

	    @ExceptionHandler(DataIntegrityViolationException.class)
	    public ResponseEntity<Map<String, String>> integrity(DataIntegrityViolationException ex) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Data conflict or value too long"));
	    }
	}

