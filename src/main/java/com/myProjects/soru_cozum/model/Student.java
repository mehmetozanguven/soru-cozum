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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SS_STUDENTS")
public class Student {
	
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
	
	public Student() {
		
	}
	
	public Student(String nonce) {
		this.name =  nonce;
	}
	
	@ManyToMany(
		fetch = FetchType.LAZY,
		cascade = {CascadeType.DETACH, CascadeType.MERGE,
				CascadeType.PERSIST, CascadeType.REFRESH}
			)
	@JoinTable(name = "SS_QUESTION_STUDENT",
		joinColumns = @JoinColumn(name = "STUDENT_ID"),
		inverseJoinColumns = @JoinColumn(name = "QUESTION_ID")
			)
	private List<Question> studentQuestions;

		
	public void addQuestionToStudent(Question question) {
		
		if (studentQuestions == null)
			studentQuestions = new ArrayList<Question>();
		
		if (!studentQuestions.contains(question))
			studentQuestions.add(question);
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

	public List<Question> getStudentQuestions() {
		return studentQuestions;
	}

	public void setStudentQuestions(List<Question> studentQuestions) {
		this.studentQuestions = studentQuestions;
	}

	
	
	
	
	
}
