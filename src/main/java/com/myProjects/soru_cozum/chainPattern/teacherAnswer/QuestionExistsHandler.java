package com.myProjects.soru_cozum.chainPattern.teacherAnswer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.AnswerQuestionResponse;

public class QuestionExistsHandler extends TeacherAnswerAbstractHandler{
	
	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		if (request.getQuestion().getId() == 0)
			return new ResponseEntity<>(new AnswerQuestionResponse("ERROR", "Invalid question id"), HttpStatus.NOT_FOUND);
		return getNextHandler().handle(request);
	}
}
