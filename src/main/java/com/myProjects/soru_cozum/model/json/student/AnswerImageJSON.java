package com.myProjects.soru_cozum.model.json.student;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;

/**
 * This class is used to show the Student to his/her answer image json address
 * @author mehmetozanguven
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerImageJSON {
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
	
	public static List<AnswerImageJSON> createAnswerImageJSONList(Question question, Publisher publisher) {
		List<AnswerImageJSON> lists = new ArrayList<AnswerImageJSON>();
		
		question.getTeacherSet().forEach(eachTeacher -> {
			AnswerImageJSON newAnswerImageJSON = new AnswerImageJSON();
			newAnswerImageJSON.setPublisherId(publisher.getId());
			newAnswerImageJSON.setQuestionCategory(question.getQuestionCategory());
			newAnswerImageJSON.setQuestionSubCategory(question.getQuestionSubCategory());
			newAnswerImageJSON.setPageNumber(question.getPageNumber());
			newAnswerImageJSON.setQuestionNumber(question.getQuestionNumber());
			newAnswerImageJSON.setQuestionId(question.getId());
			newAnswerImageJSON.setTeacherId(eachTeacher.getId());
			lists.add(newAnswerImageJSON);
		});
	
		return lists;
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
