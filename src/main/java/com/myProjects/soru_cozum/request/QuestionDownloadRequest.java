package com.myProjects.soru_cozum.request;

public class QuestionDownloadRequest {
	private Long userId;
	private String username;
	private Long publisherId;
	private Long questionNumber;
	private Long pageNumber;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}
	public Long getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(Long questionNumber) {
		this.questionNumber = questionNumber;
	}
	public Long getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
	
}
