package com.myProjects.soru_cozum.chainPattern.teacher.answer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.TeacherResponse;

public class QuestionExistsHandler extends TeacherAnswerAbstractHandler{
	
	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		if (!request.getQuestion().isPresent()) {
			getResponse().setStatu("Error");
			getResponse().setInformation(new TeacherResponse("Invalid question id"));
			return new ResponseEntity<>(getResponse(), HttpStatus.NOT_FOUND);
		}
			
		return getNextHandler().handle(request);
	}
}
