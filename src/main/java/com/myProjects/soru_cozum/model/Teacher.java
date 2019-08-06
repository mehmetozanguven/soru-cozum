package com.myProjects.soru_cozum.model;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "SS_TEACHERS")
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEACHER_ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SURNAME")
	private String surname;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
					CascadeType.REFRESH }
			)
	@JoinTable(name = "SS_QUESTION_TEACHER",
	joinColumns = @JoinColumn(name = "TEACHER_ID"),
	inverseJoinColumns = @JoinColumn(name = "QUESTION_ID")
			)
	private Set<Question> questionSet;
	
	@OneToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL
			)
	@JoinColumn(name = "ANSWER_IMAGE_ID")
	private Set<AnswerImage> answerImageSet;
	
	@OneToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL
			)
	@JoinColumn(name = "ANSWER_VIDEO_ID")
	private Set<AnswerAudio> answerAudioSet;
	
	public Teacher() {
		
	}

	public Teacher(Long id) {
		this.id = id;
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



	public String getSurname() {
		return surname;
	}



	public void setSurname(String surname) {
		this.surname = surname;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Set<Question> getQuestionSet() {
		return questionSet;
	}



	public void setQuestionSet(Set<Question> questionSet) {
		this.questionSet = questionSet;
	}



	public Set<AnswerImage> getAnswerImageSet() {
		return answerImageSet;
	}



	public void setAnswerImageSet(Set<AnswerImage> answerImageSet) {
		this.answerImageSet = answerImageSet;
	}



	public Set<AnswerAudio> getAnswerAudioSet() {
		return answerAudioSet;
	}



	public void setAnswerAudioSet(Set<AnswerAudio> answerAudioSet) {
		this.answerAudioSet = answerAudioSet;
	}



	public void addImageToTeacher(AnswerImage answerImage) {
		if (answerImageSet == null)
			answerImageSet = new TreeSet<AnswerImage>();
		answerImageSet.add(answerImage);
	}
	
	public void addAudioToTeacher(AnswerAudio answerAudio) {
		if (answerAudioSet == null)
			answerAudioSet = new TreeSet<AnswerAudio>();
		answerAudioSet.add(answerAudio);
	}
	
	public void addQuestionToTeacher(Question question) {
		if (questionSet == null)
			questionSet = new TreeSet<Question>();
		questionSet.add(question);
	}
}
