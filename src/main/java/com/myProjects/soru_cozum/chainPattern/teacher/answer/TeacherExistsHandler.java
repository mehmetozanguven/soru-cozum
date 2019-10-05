package com.myProjects.soru_cozum.chainPattern.teacher.answer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myProjects.soru_cozum.response.TeacherResponse;

public class TeacherExistsHandler extends TeacherAnswerAbstractHandler{
	private final Logger LOGGER = LoggerFactory.getLogger(TeacherExistsHandler.class);
	
	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		LOGGER.trace("Teacher will add answer image for question");
		LOGGER.trace("Checking whether teacher exists or not. Teacher: " + request.getTeacher().get());
		if (!request.getTeacher().isPresent()) {
			LOGGER.error("Teacher not exists, return invalid teacher id");
			getResponse().setStatu("Error");
			getResponse().setInformation(new TeacherResponse("Invalid teacher id"));
			return new ResponseEntity<>(getResponse(), HttpStatus.NOT_FOUND);
		}
		LOGGER.trace("Teacher found, go to the next cycle QuestionExistsHandler");
		return getNextHandler().handle(request);
	}
}
