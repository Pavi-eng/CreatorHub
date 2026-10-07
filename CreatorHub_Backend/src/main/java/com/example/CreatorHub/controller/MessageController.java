package com.example.CreatorHub.controller;

	import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.CreatorHub.entity.Message;
import com.example.CreatorHub.entity.User;
import com.example.CreatorHub.repository.MessageRepository;
import com.example.CreatorHub.repository.UserRepository;

	@RestController
	@RequestMapping("/api/messages")
	public class MessageController {
	    private final MessageRepository messages;
	    private final UserRepository users;

	    public MessageController(MessageRepository messages, UserRepository users) {
	        this.messages = messages; this.users = users;
	    }

	    @GetMapping
	    public List<Message> inbox(Authentication auth) {
	        return messages.findByRecipientIdOrderByCreatedAtDesc(me(auth).getId());
	    }

	    @PutMapping("/{id}/read")
	    public Message markRead(@PathVariable Long id, Authentication auth) {
	        Message m = owned(id, auth);
	        m.setSeen(true);
	        return messages.save(m);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
	        messages.delete(owned(id, auth));
	        return ResponseEntity.noContent().build();
	    }

	    private User me(Authentication auth) {
	        return users.findByEmail(auth.getName())
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
	    }

	    private Message owned(Long id, Authentication auth) {
	        Message m = messages.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));
	        if (!m.getRecipient().getId().equals(me(auth).getId()))
	            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your message");
	        return m;
	    }
	}

