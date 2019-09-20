package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.model.*;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;
import com.myProjects.soru_cozum.service.FileStorageService;
import com.myProjects.soru_cozum.service.PublisherService;
import com.myProjects.soru_cozum.service.QuestionService;
import com.myProjects.soru_cozum.service.StudentService;

public class StudentAskQuestionRequest {
	private Optional<Student> student;
	private Optional<Publisher> publisher;

	private int pageNumber;
	private int questionNumber;
	private String questionCategory;
	private String questionSubCategory;
	
	private MultipartFile questionMultipartFile;
	private StudentQuestionUploadResponse uploadResponse;
	
	private StudentService studentService;
	private QuestionService questionService;
	private PublisherService publisherService;
	private FileStorageService fileStorageService;

	public StudentAskQuestionRequest(StudentService studentService, QuestionService questionService,
			PublisherService publisherService, FileStorageService fileStorageService) {
		this.studentService = studentService;
		this.questionService = questionService;
		this.publisherService = publisherService;
		this.fileStorageService = fileStorageService;
	}

	public Optional<Student> getStudent() {
		return student;
	}

	public void setStudent(Optional<Student> student) {
		this.student = student;
	}

	public Optional<Publisher> getPublisher() {
		return publisher;
	}

	public void setPublisher(Optional<Publisher> publisher) {
		this.publisher = publisher;
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

	public FileStorageService getFileStorageService() {
		return fileStorageService;
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

	public String getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(String questionCategory) {
		this.questionCategory = questionCategory;
	}

	public String getQuestionSubCategory() {
		return questionSubCategory;
	}

	public void setQuestionSubCategory(String subQuestionCategory) {
		this.questionSubCategory = subQuestionCategory;
	}

	public MultipartFile getQuestionMultipartFile() {
		return questionMultipartFile;
	}

	public void setQuestionMultipartFile(MultipartFile questionMultipartFile) {
		this.questionMultipartFile = questionMultipartFile;
	}

	public StudentQuestionUploadResponse getUploadResponse() {
		return uploadResponse;
	}

	public void setUploadResponse(StudentQuestionUploadResponse uploadResponse) {
		this.uploadResponse = uploadResponse;
	}
	
	

}
