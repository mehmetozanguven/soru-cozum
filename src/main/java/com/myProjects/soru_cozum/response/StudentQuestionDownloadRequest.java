package com.myProjects.soru_cozum.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentQuestionDownloadRequest {
	@JsonAlias("publisherId")
	private Long publisherId;
	
	@JsonAlias("questionCategory")
	private String questionCategory;
	
	@JsonAlias("questionSubCategory")
	private String questionSubCategory;
	
	@JsonAlias("pageNumber")
	private int pageNumber;
	
	@JsonAlias("questionNumber")
	private int questionNumber;


	public Long getPublisherId() {
		return publisherId;
	}

	public String getQuestionCategory() {
		return questionCategory;
	}

	public String getQuestionSubCategory() {
		return questionSubCategory;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	@Override
	public String toString() {
		return "StudentQuestionDownloadRequest [ /publisherId=" + publisherId
				+ "/ questionCategory=" + questionCategory + "/ questionSubCategory=" + questionSubCategory
				+ "/ pageNumber=" + pageNumber + "/ questionNumber=" + questionNumber + "]";
	}

}
