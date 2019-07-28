package com.myProjects.soru_cozum.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "SS_QUESTIONS")

public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUESTION_ID")
	private Long id;

	@Column(name = "IS_ANSWERED")
	private boolean isAnswered;

	@Column(name = "PAGE_NUMBER")
	private int pageNumber;

	@Column(name = "QUESTION_NUMBER")
	private int questionNumber;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "SS_QUESTION_STUDENT", 
	joinColumns = @JoinColumn(name = "QUESTION_ID"), 
	inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
	private List<Student> studentList;
	
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY,
			cascade =  { CascadeType.ALL }
			)
	@JoinColumn(name = "QUESTION_IMAGE_ID")
	private QuestionImage questionImage;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
					CascadeType.REFRESH }
			)
	@JoinTable(name = "SS_QUESTION_TEACHER",
			joinColumns = @JoinColumn(name = "QUESTION_ID"),
			inverseJoinColumns = @JoinColumn(name = "TEACHER_ID")
			)
	private List<Teacher> teacherList;
		
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
					CascadeType.REFRESH })
	@JoinColumn(name = "PUBLISH_ID")
	private Publisher publisher;
	
	public Question() {
		
	}
	
	public Question(long id) {
		this.id = id;
	}
	
	public void addTeacherToQuestion(Teacher teacher) {
		if (teacherList == null)
			teacherList = new ArrayList<>();
		if (!teacherList.contains(teacher))
			teacherList.add(teacher);
	}
	
	public void addPublisherToQuestion(Publisher publisher) {
		this.publisher = publisher;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isAnswered() {
		return isAnswered;
	}

	public void setAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public QuestionImage getQuestionImage() {
		return questionImage;
	}

	public void setQuestionImage(QuestionImage questionImage) {
		this.questionImage = questionImage;
	}

	public List<Teacher> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(List<Teacher> teacherList) {
		this.teacherList = teacherList;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", isAnswered=" + isAnswered + ", pageNumber=" + pageNumber + ", questionNumber="
				+ questionNumber + ", studentList=" + studentList + ", questionImage=" + questionImage
				+ ", teacherList=" + teacherList + ", publisher=" + publisher + "]";
	}

	
	

}
