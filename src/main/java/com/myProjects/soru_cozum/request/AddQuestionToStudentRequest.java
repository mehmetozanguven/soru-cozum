package com.myProjects.soru_cozum.request;



import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;

public class AddQuestionToStudentRequest {
	@JsonAlias({"StudentId"})
	private int studentId;
	
	@JsonAlias("Publisher")
	private Publisher publisher;
	
	@JsonAlias("PageNumber")
	private int pageNumber;
	
	@JsonAlias("QuestionNumber")
	private int questionNumber;
	
	//@JsonAlias("ImageByte")
	@JsonIgnore
	private byte[] imageByte;
	
	// temporal, delete when unnecessary
	@JsonAlias("ImageByte")
	private String filePath;
	
	@JsonAlias("QuestionCategory")
	private QuestionCategory questionCategory;
	
	// change the enum value after
	@JsonAlias("QuestionSubCategory")
	private String questionSubCategory;
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public byte[] getImageByte() {
		return imageByte;
	}

	public void setImageByte(byte[] imageByte) {
		this.imageByte = imageByte;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisherId(Publisher publisher) {
		this.publisher = publisher;
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

	public QuestionCategory getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(QuestionCategory questionCategory) {
		this.questionCategory = questionCategory;
	}

	public String getQuestionSubCategory() {
		return questionSubCategory;
	}

	public void setQuestionSubCategory(String questionSubCategory) {
		this.questionSubCategory = questionSubCategory;
	}
	
	
	
	
	
}
