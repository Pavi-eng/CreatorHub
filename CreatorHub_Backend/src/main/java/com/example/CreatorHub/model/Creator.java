package com.example.CreatorHub.model;
	
import jakarta.persistence.*;

@Entity
@Table(name = "creators")
public class Creator {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String name;
	    private String email;
	    private String platform;
	    private String category;

	    public Creator() {}

	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }
	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }
	    public String getPlatform() { return platform; }
	    public void setPlatform(String platform) { this.platform = platform; }
	    public String getCategory() { return category; }
	    public void setCategory(String category) { this.category = category; }
	}

