package com.myProjects.soru_cozum.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "SS_ANSWER_IMAGE")

public class AnswerImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ANSWER_IMAGE_ID")
	private Long id;
	
	@Column(name = "IMAGE")
	private Blob image;
	
	public AnswerImage() {
		
	}
}
