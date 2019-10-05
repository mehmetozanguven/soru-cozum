package com.myProjects.soru_cozum.request.service;

import org.springframework.web.multipart.MultipartFile;

public class TeacherAnswerAudioUploadServiceRequest {

	private MultipartFile answerAudioFile;

	private Long publisherId;

	private String questionCategory;
	private String questionSubCategory;

	private Integer pageNumber;
	private Integer questionNumber;

	private Long teacherId;
	private Long questionId;

	public MultipartFile getAnswerAudioFile() {
		return answerAudioFile;
	}

	public void setAnswerAudioFile(MultipartFile answerAudioFile) {
		this.answerAudioFile = answerAudioFile;
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

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "TeacherAnswerAudioUploadServiceRequest [answerAudioFile=" + answerAudioFile + ", publisherId="
				+ publisherId + ", questionCategory=" + questionCategory + ", questionSubCategory="
				+ questionSubCategory + ", pageNumber=" + pageNumber + ", questionNumber=" + questionNumber
				+ ", teacherId=" + teacherId + ", questionId=" + questionId + "]";
	}
	
	

}
