package com.example.CreatorHub.entity;

	import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

	@Entity
	@Table(name = "projects")
	@Getter @Setter
	public class Project extends OwnedEntity {
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
		public String getTechStack() {
			return techStack;
		}
		public void setTechStack(String techStack) {
			this.techStack = techStack;
		}
		public String getProjectUrl() {
			return projectUrl;
		}
		public void setProjectUrl(String projectUrl) {
			this.projectUrl = projectUrl;
		}
		public String getRepoUrl() {
			return repoUrl;
		}
		public void setRepoUrl(String repoUrl) {
			this.repoUrl = repoUrl;
		}
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		private String title;
	    @Column(length = 2000)
	    private String description;
	    private String techStack;
	    private String projectUrl;
	    private String repoUrl;
	    @Column(length = 500)
	    private String imageUrl;
	}

