package com.myProjects.soru_cozum.model;

import javax.persistence.CascadeType;
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
@Table(name = "SS_ANSWER_IMAGE")
public class AnswerImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ANSWER_IMAGE_ID")
	private Long id;

	@Column(name = "PUBLISH_ID")
	private Long associatePublisherId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinColumn(name = "TEACHER_ID")
	private Teacher teacher;

	@Column(name = "QUESTION_ID")
	private Long associatedQuestionId;

	public AnswerImage() {

	}

	public AnswerImage(Long associatePublisherId, Long associatedQuestionId) {
		this.associatePublisherId = associatePublisherId;
		this.associatedQuestionId = associatedQuestionId;
	}

	public AnswerImage(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssociatePublisherId() {
		return associatePublisherId;
	}

	public void setAssociatePublisherId(Long associatePublisherId) {
		this.associatePublisherId = associatePublisherId;
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

	@Override
	public String toString() {
		return "AnswerImage [id=" + id + " teacher=" + teacher + ", associatedQuestionId=" + associatedQuestionId + "]";
	}

}
