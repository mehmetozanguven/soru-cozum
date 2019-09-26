package com.myProjects.soru_cozum.model.json.student;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionImageJSON {
	@JsonAlias("publisherId")
	private Long publisherId;
	
	@JsonAlias("questionCategory")
	private String questionCategory;
	@JsonAlias("questionSubCategory")
	private String questionSubCategory;

	@JsonAlias("pageNumber")
	private Integer pageNumber;
	@JsonAlias("questionNumber")
	private Integer questionNumber;
	
	public static QuestionImageJSON createQuestionJSON(Question question, Publisher publisher) {
		QuestionImageJSON newQuestionImageJSON = new QuestionImageJSON();
		newQuestionImageJSON.setPublisherId(publisher.getId());
		newQuestionImageJSON.setQuestionCategory(question.getQuestionCategory());
		newQuestionImageJSON.setQuestionSubCategory(question.getQuestionSubCategory());
		newQuestionImageJSON.setPageNumber(question.getPageNumber());
		newQuestionImageJSON.setQuestionNumber(question.getQuestionNumber());
		return newQuestionImageJSON;
	}
	
	
	public Long getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}
	public String getQuestionCategory() {
		return questionCategory;
	}
	public void setQuestionCategory(String questionCategory) {
		this.questionCategory = questionCategory;
	}
	public String getQuestionSubCategory() {
		return questionSubCategory;
	}
	public void setQuestionSubCategory(String questionSubCategory) {
		this.questionSubCategory = questionSubCategory;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}
	
	
}
