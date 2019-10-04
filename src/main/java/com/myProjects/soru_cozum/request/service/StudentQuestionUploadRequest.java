package com.myProjects.soru_cozum.request.service;

import org.springframework.web.multipart.MultipartFile;

public class StudentQuestionUploadRequest {

	private MultipartFile questionImageFile;

	private Long publisherId;

	private String questionCategory;
	private String questionSubCategory;

	private Integer pageNumber;
	private Integer questionNumber;

	public MultipartFile getQuestionImageFile() {
		return questionImageFile;
	}

	public void setQuestionImageFile(MultipartFile questionImageFile) {
		this.questionImageFile = questionImageFile;
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
