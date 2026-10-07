package com.example.CreatorHub.entity;

	import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

	@Entity
	@Table(name = "achievements")
	@Getter @Setter
	public class Achievement extends OwnedEntity {
	    private String title;
	    public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public LocalDate getAchievedDate() {
			return achievedDate;
		}
		public void setAchievedDate(LocalDate achievedDate) {
			this.achievedDate = achievedDate;
		}
		@Column(length = 2000)
	    private String description;
	    private LocalDate achievedDate;
	}

