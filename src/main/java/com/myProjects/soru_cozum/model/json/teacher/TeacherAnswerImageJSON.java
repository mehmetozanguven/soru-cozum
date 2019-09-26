package com.myProjects.soru_cozum.model.json.teacher;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;

public class TeacherAnswerImageJSON {
	
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

	@JsonAlias("teacherId")
	private Long teacherId;
	@JsonAlias("questionId")
	private Long questionId;
	
	public static TeacherAnswerImageJSON createTeacherAnswerAudioJSON(Question question, Publisher publisher, Long teacherID) {
		TeacherAnswerImageJSON newTeacherAnswerImageJSON = new TeacherAnswerImageJSON();
		newTeacherAnswerImageJSON.setPublisherId(publisher.getId());
		newTeacherAnswerImageJSON.setQuestionCategory(question.getQuestionCategory());
		newTeacherAnswerImageJSON.setQuestionSubCategory(question.getQuestionSubCategory());
		newTeacherAnswerImageJSON.setPageNumber(question.getPageNumber());
		newTeacherAnswerImageJSON.setQuestionNumber(question.getQuestionNumber());
		newTeacherAnswerImageJSON.setTeacherId(teacherID);
		newTeacherAnswerImageJSON.setQuestionId(question.getId());
		
		return newTeacherAnswerImageJSON;
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
	
}
