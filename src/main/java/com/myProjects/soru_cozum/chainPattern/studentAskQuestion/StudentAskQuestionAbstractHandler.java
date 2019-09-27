package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.request.AddQuestionRequest;
import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.response.StudentAskQuestionResponse;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;
import com.myProjects.soru_cozum.service.QuestionService;

public abstract class StudentAskQuestionAbstractHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentAskQuestionAbstractHandler.class);
	
	private StudentAskQuestionAbstractHandler nextHandler;
	
	private GenericResponse<StudentAskQuestionResponse> response;
	
	
	
	public StudentAskQuestionAbstractHandler() {
		response = new GenericResponse<StudentAskQuestionResponse>();
	}

	public abstract ResponseEntity<?> handle(StudentAskQuestionRequest request);
	
	public void setNextHandler(StudentAskQuestionAbstractHandler handler) {
		this.nextHandler = handler;
	}
	
	public StudentAskQuestionAbstractHandler getNextHandler() {
		return this.nextHandler;
	}	
	
	public GenericResponse<StudentAskQuestionResponse> getResponse() {
		return response;
	}
	
	public StudentQuestionUploadResponse prepareQuestionDownloadJson(StudentAskQuestionRequest request) {
		Long publisherId = request.getPublisher().get().getId();
		StudentQuestionUploadResponse imageDownloadJson = new StudentQuestionUploadResponse(publisherId, request.getQuestionCategory(),
				request.getQuestionSubCategory(), request.getPageNumber(),
				request.getQuestionNumber());
		return imageDownloadJson;
	}

	public Question createNewQuestion(int pageNumber, int questionNumber, String questionCategory, String questionSubCategory,
			Publisher newPublisher, QuestionService questionService) {
		
		Question newQuestion = questionService.createNewQuestionWithCommonProperties_multipart(pageNumber, questionNumber,
				questionCategory, questionSubCategory);
		return newQuestion;

	}
}
