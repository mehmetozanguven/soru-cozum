package com.myProjects.soru_cozum.chainPattern.teacherAnswer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.AnswerQuestionResponse;

public class TeacherExistsHandler extends TeacherAnswerHandler{

	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		if (request.getTeacher().getId() == 0)
			return new ResponseEntity<>(new AnswerQuestionResponse("ERROR", "Invalid teacher id"), HttpStatus.NOT_FOUND);
		return getNextHandler().handle(request);
	}
}
