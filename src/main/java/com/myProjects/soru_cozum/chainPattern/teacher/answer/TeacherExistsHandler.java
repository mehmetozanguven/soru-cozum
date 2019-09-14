package com.myProjects.soru_cozum.chainPattern.teacher.answer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.TeacherResponse;

public class TeacherExistsHandler extends TeacherAnswerAbstractHandler{

	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		if (request.getTeacher().getId() == 0) {
			getResponse().setStatu("Success");
			getResponse().setInformation(new TeacherResponse("Invalid teacher id"));
			return new ResponseEntity<>(getResponse(), HttpStatus.NOT_FOUND);
		}
		return getNextHandler().handle(request);
	}
}
