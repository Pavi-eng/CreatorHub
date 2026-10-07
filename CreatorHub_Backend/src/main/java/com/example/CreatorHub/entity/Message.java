package com.example.CreatorHub.entity;

	import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

	@Entity
	@Table(name = "messages")
	@Getter @Setter
	public class Message {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getSenderName() {
			return senderName;
		}

		public void setSenderName(String senderName) {
			this.senderName = senderName;
		}

		public String getSenderEmail() {
			return senderEmail;
		}

		public void setSenderEmail(String senderEmail) {
			this.senderEmail = senderEmail;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public boolean isSeen() {
			return seen;
		}

		public void setSeen(boolean seen) {
			this.seen = seen;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public User getRecipient() {
			return recipient;
		}

		public void setRecipient(User recipient) {
			this.recipient = recipient;
		}

		private String senderName;
		private String senderUsername;
		public String getSenderUsername() {
			return senderUsername;
		}

		public void setSenderUsername(String senderUsername) {
			this.senderUsername = senderUsername;
		}

		public String getSenderRole() {
			return senderRole;
		}

		public void setSenderRole(String senderRole) {
			this.senderRole = senderRole;
		}

		private String senderRole;
	    private String senderEmail;
	    private String subject;
	    @Column(length = 3000)
	    private String content;
	    private boolean seen;
	    private LocalDateTime createdAt;

	    @ManyToOne
	    @JoinColumn(name = "recipient_id")
	    @JsonIgnore
	    private User recipient;

	    @PrePersist
	    void onCreate() { createdAt = LocalDateTime.now(); }

		
			
		}
	

