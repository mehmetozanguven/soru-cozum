package com.myProjects.soru_cozum.model;

import java.sql.Blob;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "SS_PUBLISHER")
public class Publisher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PUBLISH_ID")
	private Long id;
	
	@Column(name = "PUBLISHER_NAME")
	private String name;
	
	@Column(name = "PUBLISH_YEAR")
	private int publishYear;
	
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			mappedBy = "publisher" )
	private List<Question> questionList;
	
	public Publisher() {
		
	}
	
	public Publisher(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	@Override
	public String toString() {
		return "Publisher [id=" + id + ", name=" + name + ", publishYear=" + publishYear + "]";
	}
	
	
	
	
}
