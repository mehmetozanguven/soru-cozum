package com.myProjects.soru_cozum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "SS_ANSWER_VIDEO")
public class AnswerAudio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ANSWER_VIDEO_ID")
	private Long id;
	
	@Column(name = "IMAGE")
	private byte[] image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID")
	private Teacher teacher;
	
	@Column(name = "QUESTION_ID")
	private Long associatedQuestionId;
	
	public AnswerAudio() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Long getAssociatedQuestionId() {
		return associatedQuestionId;
	}

	public void setAssociatedQuestionId(Long associatedQuestionId) {
		this.associatedQuestionId = associatedQuestionId;
	}

	
	
	
}
