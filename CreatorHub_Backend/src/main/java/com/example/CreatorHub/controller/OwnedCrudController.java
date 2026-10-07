package com.example.CreatorHub.controller;

	import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.CreatorHub.entity.OwnedEntity;
import com.example.CreatorHub.entity.User;
import com.example.CreatorHub.repository.OwnedRepository;
import com.example.CreatorHub.repository.UserRepository;

	public abstract class OwnedCrudController<T extends OwnedEntity> {
	    protected final OwnedRepository<T> repo;
	    protected final UserRepository users;

	    protected OwnedCrudController(OwnedRepository<T> repo, UserRepository users) {
	        this.repo = repo; this.users = users;
	    }

	    protected abstract void copy(T from, T to);

	    @GetMapping
	    public List<T> mine(Authentication auth) {
	        return repo.findByOwnerId(currentUser(auth).getId());
	    }

	    @PostMapping
	    public T create(@RequestBody T body, Authentication auth) {
	        body.setId(null);
	        body.setOwner(currentUser(auth));
	        return repo.save(body);
	    }

	    @PutMapping("/{id}")
	    public T update(@PathVariable Long id, @RequestBody T body, Authentication auth) {
	        T existing = owned(id, auth);
	        copy(body, existing);
	        return repo.save(existing);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
	        repo.delete(owned(id, auth));
	        return ResponseEntity.noContent().build();
	    }

	    protected User currentUser(Authentication auth) {
	        return users.findByEmail(auth.getName())
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
	    }

	    private T owned(Long id, Authentication auth) {
	        T item = repo.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
	        if (!item.getOwner().getId().equals(currentUser(auth).getId()))
	            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your item");
	        return item;
	    }
	}

