package com.myProjects.soru_cozum.model.json.teacher;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeacherAnswerAudioJSON {
	
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
	
	public static TeacherAnswerAudioJSON createTeacherAnswerAudioJSON(Question question, Publisher publisher, Long teacherID) {
		TeacherAnswerAudioJSON newTeacherAnswerAudioJSON = new TeacherAnswerAudioJSON();
		newTeacherAnswerAudioJSON.setPublisherId(publisher.getId());
		newTeacherAnswerAudioJSON.setQuestionCategory(question.getQuestionCategory());
		newTeacherAnswerAudioJSON.setQuestionSubCategory(question.getQuestionSubCategory());
		newTeacherAnswerAudioJSON.setPageNumber(question.getPageNumber());
		newTeacherAnswerAudioJSON.setQuestionNumber(question.getQuestionNumber());
		newTeacherAnswerAudioJSON.setTeacherId(teacherID);
		newTeacherAnswerAudioJSON.setQuestionId(question.getId());
		
		return newTeacherAnswerAudioJSON;
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
		return "TeacherAnswerAudioJSON [publisherId=" + publisherId + ", questionCategory=" + questionCategory
				+ ", questionSubCategory=" + questionSubCategory + ", pageNumber=" + pageNumber + ", questionNumber="
				+ questionNumber + ", teacherId=" + teacherId + ", questionId=" + questionId + "]";
	}
	
	
}
