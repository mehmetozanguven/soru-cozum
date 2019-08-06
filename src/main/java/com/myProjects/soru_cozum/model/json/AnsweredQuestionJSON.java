package com.myProjects.soru_cozum.model.json;

import java.util.Set;

public class AnsweredQuestionJSON {
	
	private PublisherJSON publisherJSON;
	private Set<StudentJSON> setOfStudents;
	private QuestionJSON questionJSON;
	private int questionNumber;
	private int pageNumber;
	
	
	
	public AnsweredQuestionJSON(PublisherJSON publisherJSON, Set<StudentJSON> setOfStudents, QuestionJSON questionJSON,
			int questionNumber, int pageNumber) {
		this.publisherJSON = publisherJSON;
		this.setOfStudents = setOfStudents;
		this.questionJSON = questionJSON;
		this.questionNumber = questionNumber;
		this.pageNumber = pageNumber;
	}
	public PublisherJSON getPublisherJSON() {
		return publisherJSON;
	}
	public void setPublisherJSON(PublisherJSON publisherJSON) {
		this.publisherJSON = publisherJSON;
	}
	public Set<StudentJSON> getSetOfStudents() {
		return setOfStudents;
	}
	public void setSetOfStudents(Set<StudentJSON> setOfStudents) {
		this.setOfStudents = setOfStudents;
	}
	public QuestionJSON getQuestionJSON() {
		return questionJSON;
	}
	public void setQuestionJSON(QuestionJSON questionJSON) {
		this.questionJSON = questionJSON;
	}
	public int getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
	
}
