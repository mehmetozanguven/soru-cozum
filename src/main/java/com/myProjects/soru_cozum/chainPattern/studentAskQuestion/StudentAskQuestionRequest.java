package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;


import com.myProjects.soru_cozum.model.*;
import com.myProjects.soru_cozum.request.AddQuestionRequest;
import com.myProjects.soru_cozum.service.FileStorageService;
import com.myProjects.soru_cozum.service.PublisherServiceImpl;
import com.myProjects.soru_cozum.service.QuestionService;
import com.myProjects.soru_cozum.service.StudentService;

public class StudentAskQuestionRequest {
	private Student student;
	private Publisher publisher;

	private AddQuestionRequest addQuestionToStudentRequest;
	private StudentService studentService;
	private QuestionService questionService;
	private PublisherServiceImpl publisherService;
	private FileStorageService fileStorageService;

	public StudentAskQuestionRequest(StudentService studentService, QuestionService questionService,
			PublisherServiceImpl publisherService, FileStorageService fileStorageService) {
		this.studentService = studentService;
		this.questionService = questionService;
		this.publisherService = publisherService;
		this.fileStorageService = fileStorageService;
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

	public AddQuestionRequest getAddQuestionToStudentRequest() {
		return addQuestionToStudentRequest;
	}

	public void setAddQuestionToStudentRequest(AddQuestionRequest addQuestionToStudentRequest) {
		this.addQuestionToStudentRequest = addQuestionToStudentRequest;
	}

	public StudentService getStudentService() {
		return studentService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public PublisherServiceImpl getPublisherService() {
		return publisherService;
	}

	public FileStorageService getFileStorageService() {
		return fileStorageService;
	}

}
