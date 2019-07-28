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
	private List<Question> questionList;
	
	@OneToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL
			)
	@JoinColumn(name = "ANSWER_IMAGE_ID")
	private List<AnswerImage> answerImageList;
	
	public Teacher() {
		
	}
	
	public void addImageToTeacher(AnswerImage answerImage) {
		if (answerImageList == null)
			answerImageList = new ArrayList<AnswerImage>();
		answerImageList.add(answerImage);
	}
	
}
