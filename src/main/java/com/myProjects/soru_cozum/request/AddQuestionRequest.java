package com.myProjects.soru_cozum.request;



import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;

public class AddQuestionRequest {
	@JsonAlias({"StudentId"})
	private Long studentId;
	
	@NotNull
	@JsonAlias("Publisher")
	private Publisher publisher;
	
	@NotNull
	@JsonAlias("PageNumber")
	private int pageNumber;
	
	@NotNull
	@JsonAlias("QuestionNumber")
	private int questionNumber;
	
	@JsonAlias("questionUrl")
	private String questionUrl;
	
//	@JsonAlias("ImageByte")
////	@JsonIgnore
//	private MultipartFile imageByte;
	
	// temporal, delete when unnecessary
/*	@JsonAlias("ImageByte")
	private String filePath;*/
	
	@NotNull
	@JsonAlias("QuestionCategory")
	private QuestionCategory questionCategory;
	
	// change the enum value after
	@JsonAlias("QuestionSubCategory")
	private String questionSubCategory;
	
/*	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}*/

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

//	public MultipartFile getImageByte() {
//		return imageByte;
//	}
//
//	public void setImageByte(MultipartFile imageByte) {
//		this.imageByte = imageByte;
//	}

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

	public String getQuestionUrl() {
		return questionUrl;
	}

	public void setUrl(String url) {
		this.questionUrl = url;
	}
	
	
	
	
	
}
