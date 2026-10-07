package com.example.CreatorHub.entity;

	import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

	@Entity
	@Table(name = "certifications")
	@Getter @Setter
	public class Certification extends OwnedEntity {
	    private String title;
	    private String issuer;
	    private LocalDate issueDate;
	    private String credentialUrl;
	    
	    public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getIssuer() {
			return issuer;
		}
		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}
		public LocalDate getIssueDate() {
			return issueDate;
		}
		public void setIssueDate(LocalDate issueDate) {
			this.issueDate = issueDate;
		}
		public String getCredentialUrl() {
			return credentialUrl;
		}
		public void setCredentialUrl(String credentialUrl) {
			this.credentialUrl = credentialUrl;
		}
		
	}

