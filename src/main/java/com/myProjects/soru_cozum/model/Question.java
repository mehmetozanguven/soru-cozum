package com.myProjects.soru_cozum.model;

import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myProjects.soru_cozum.enums.QuestionCategory;


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

	@Column(name = "QUESTION_CATEGORY", nullable = false)
	private String questionCategory;
	
	@Column(name = "QUESTION_SUB_CATEGORY", nullable = true)
	private String questionSubCategory;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "SS_QUESTION_STUDENT", 
	joinColumns = @JoinColumn(name = "QUESTION_ID"), 
	inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
	private List<Student> studentList;
	
	@Column(name = "QUESTION_DOWNLOAD_URI", nullable = false)
	private String questionDownloadUri;
	
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
	private Set<Teacher> teacherSet;
		
	
	@ManyToOne(fetch = FetchType.EAGER,
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
		if (teacherSet== null)
			teacherSet = new TreeSet<>();
		
		teacherSet.add(teacher);
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

	public Set<Teacher> getTeacherSet() {
		return teacherSet;
	}

	public void setTeacherSet(Set<Teacher> teacherSet) {
		this.teacherSet = teacherSet;
	}

	public void setQuestionCategory(String questionCategory) {
		this.questionCategory = questionCategory;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public String getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(QuestionCategory questionCategory) {
		this.questionCategory = questionCategory.getValue();
	}

	public String getQuestionSubCategory() {
		return questionSubCategory;
	}

	public void setQuestionSubCategory(String questionSubCategory) {
		this.questionSubCategory = questionSubCategory;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getQuestionDownloadUri() {
		return questionDownloadUri;
	}

	public void setQuestionDownloadUri(String questionDownloadUri) {
		this.questionDownloadUri = questionDownloadUri;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", isAnswered=" + isAnswered + ", pageNumber=" + pageNumber + ", questionNumber="
				+ questionNumber + ", studentList=" + studentList + ", questionImage=" + questionImage
				+ ", teacherList=" + teacherSet + ", publisher=" + publisher + "]";
	}

	
	

}
