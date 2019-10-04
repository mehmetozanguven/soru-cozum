package com.myProjects.soru_cozum.response;

public class StudentQuestionUploadResponse {
	private Long publisherId;
	private String questionCategory;
	private String questionSubCategory;
	private int pageNumber;
	private int questionNumber;
	
	public StudentQuestionUploadResponse() {
	}
	
	public StudentQuestionUploadResponse(Long publisherId, String questionCategory,
			String questionSubCategory, int pageNumber, int questionNumber) {
		
		this.publisherId = publisherId;
		this.questionCategory = questionCategory;
		this.questionSubCategory = questionSubCategory;
		this.pageNumber = pageNumber;
		this.questionNumber = questionNumber;
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

	@Override
	public String toString() {
		return "StudentQuestionUploadResponse [/ publisherId=" + publisherId
				+ "/ questionCategory=" + questionCategory + "/ questionSubCategory=" + questionSubCategory
				+ "/ pageNumber=" + pageNumber + "/ questionNumber=" + questionNumber + "]";
	}
	
	

}
