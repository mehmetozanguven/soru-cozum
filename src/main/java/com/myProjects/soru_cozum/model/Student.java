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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "SS_STUDENTS")
public class Student {
	private static final Logger LOGGER = LoggerFactory.getLogger(Student.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STUDENT_ID")
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
	@JoinColumn(name = "STUDENT_DETAILS_ID")
	private StudentDetails studentDetails;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "SS_QUESTION_STUDENT", joinColumns = @JoinColumn(name = "STUDENT_ID"), inverseJoinColumns = @JoinColumn(name = "QUESTION_ID"))
	private Set<Question> studentQuestions;

	public Student() {
		studentQuestions =  new TreeSet<Question>();
	}

	public Student(String nonce) {
		this.name = nonce;
	}

	public void addQuestionToStudent(Question question) {
		if (studentQuestions == null)
			studentQuestions = new TreeSet<Question>();
		LOGGER.debug("Size of student question list, before adding question to Student:" + studentQuestions.size());
		studentQuestions.add(question);
		LOGGER.debug("Size of student question list, after adding question to Student:" + studentQuestions.size());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StudentDetails getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
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

	public Set<Question> getStudentQuestions() {
		return studentQuestions;
	}

	public void setStudentQuestions(Set<Question> studentQuestions) {
		this.studentQuestions = studentQuestions;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
