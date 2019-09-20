package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.StudentAskQuestionResponse;

public class StudentExistsHandler extends StudentAskQuestionAbstractHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentExistsHandler.class);
	
	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequest request) {
		LOGGER.debug("1. First check student exists or not");
			
		if (!request.getStudent().isPresent()) {
			LOGGER.debug("Student not exists");
			getResponse().setStatu("Error");
			getResponse().setInformation(new StudentAskQuestionResponse("Invalid Student ID"));
			return new ResponseEntity<>(getResponse(), HttpStatus.BAD_REQUEST);
		}
		else {
			LOGGER.debug("Student exists, then go to next cycle -check publisher-");
			return getNextHandler().handle(request);
		}
	}
 
}
