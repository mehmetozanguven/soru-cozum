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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "SS_TEACHERS")
public class Teacher implements Comparable<Teacher> {
	private final static Logger LOGGER = LoggerFactory.getLogger(Teacher.class);

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

	@Column(name = "USERNAME")
	private String username;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TEACHER_DETAILS_ID")
	private TeacherDetails teacherDetails;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "SS_QUESTION_TEACHER", joinColumns = @JoinColumn(name = "TEACHER_ID"), inverseJoinColumns = @JoinColumn(name = "QUESTION_ID"))
	private Set<Question> questionSet;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "teacher")
	private Set<AnswerImage> answerImageSet;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "teacher")
	private Set<AnswerAudio> answerAudioSet;

	public Teacher() {

	}
	
	@Override
	public int compareTo(Teacher o) {
		if (this.id > o.id)
			return 1;
		else if (this.id < o.id)
			return -1;
		else
			return 0;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public TeacherDetails getTeacherDetails() {
		return teacherDetails;
	}

	public void setTeacherDetails(TeacherDetails teacherDetails) {
		this.teacherDetails = teacherDetails;
	}

	public void addImageToTeacher(AnswerImage answerImage) {
		if (answerImageSet == null)
			answerImageSet = new TreeSet<AnswerImage>();
		answerImageSet.add(answerImage);
		answerImage.setTeacher(this);
	}

	public void addAudioToTeacher(AnswerAudio answerAudio) {
		if (answerAudioSet == null)
			answerAudioSet = new TreeSet<AnswerAudio>();
		answerAudioSet.add(answerAudio);
		answerAudio.setTeacher(this);
	}

	public void addQuestionToTeacher(Question question) {
		if (questionSet == null)
			questionSet = new TreeSet<Question>();
		questionSet.add(question);
	}

	public void updateAnswerImage(AnswerImage oldAnswerImage, AnswerImage newAnswerImage) {
		if (answerImageSet.contains(oldAnswerImage)) {
			LOGGER.debug("Answer image size before deleting: " + answerImageSet.size());
			LOGGER.debug("Deleting existing answer image: " + oldAnswerImage);
			answerImageSet.remove(oldAnswerImage);
			LOGGER.debug("Answer image size after deleting: " + answerImageSet.size());
		}
		LOGGER.debug("Adding new updated answer image: " + newAnswerImage);
		answerImageSet.add(newAnswerImage);
		newAnswerImage.setTeacher(this);
	}
	
	public void updateAnswerAudio(AnswerAudio oldAnswerAudio, AnswerAudio newAnswerAudio) {
		if (answerAudioSet.contains(oldAnswerAudio)) {
			LOGGER.debug("Answer audio size before deleting: " + answerAudioSet.size());
			LOGGER.debug("Deleting existing answer image: " + oldAnswerAudio);
			answerAudioSet.remove(oldAnswerAudio);
			LOGGER.debug("Answer image size after deleting: " + answerAudioSet.size());
		}
		LOGGER.debug("Adding new updated answer image: " + newAnswerAudio);
		answerAudioSet.add(newAnswerAudio);
		newAnswerAudio.setTeacher(this);
	}

	@Override
	public String toString() {
		return "Id: " + id + " username: " + username + " name: " + name;
	}
}
