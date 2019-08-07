package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import com.myProjects.soru_cozum.model.*;
import com.myProjects.soru_cozum.request.AddQuestionToStudentRequest;
import com.myProjects.soru_cozum.service.PublisherService;
import com.myProjects.soru_cozum.service.QuestionService;
import com.myProjects.soru_cozum.service.StudentService;

public class StudentAskQuestionRequestHandler {
	private Student student;
	private Publisher publisher;

	private AddQuestionToStudentRequest addQuestionToStudentRequest;
	private StudentService studentService;
	private QuestionService questionService;
	private PublisherService publisherService;
	
	
	public StudentAskQuestionRequestHandler(StudentService studentService, QuestionService questionService, PublisherService publisherService) {
		this.studentService = studentService;
		this.questionService = questionService;
		this.publisherService = publisherService;
	}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public AddQuestionToStudentRequest getAddQuestionToStudentRequest() {
		return addQuestionToStudentRequest;
	}
	public void setAddQuestionToStudentRequest(AddQuestionToStudentRequest addQuestionToStudentRequest) {
		this.addQuestionToStudentRequest = addQuestionToStudentRequest;
	}
	public StudentService getStudentService() {
		return studentService;
	}
	public QuestionService getQuestionService() {
		return questionService;
	}
	public PublisherService getPublisherService() {
		return publisherService;
	}
	
}
