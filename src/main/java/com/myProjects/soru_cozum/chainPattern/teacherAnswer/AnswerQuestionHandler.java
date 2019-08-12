package com.myProjects.soru_cozum.chainPattern.teacherAnswer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.response.AnswerQuestionResponse;

public class AnswerQuestionHandler extends TeacherAnswerAbstractHandler{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AnswerQuestionHandler.class);
	
	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		request.getTeacherService().resolveTeacherAccordingToAnswerQuestion(request.getTeacher(), request.getQuestion(), request.getAnswerQuestionRequest());
				
		request.getQuestion().addTeacherToQuestion(request.getTeacher());
		request.getQuestion().setAnswered(true);
		request.getQuestionService().updateQuestion(request.getQuestion());
		
		return new ResponseEntity<>(new AnswerQuestionResponse("SUCCESS", "Your answer was sent"), HttpStatus.OK);
	}
}
