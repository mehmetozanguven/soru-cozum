package com.myProjects.soru_cozum.chainPattern.teacher.answer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.TeacherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionExistsHandler extends TeacherAnswerAbstractHandler{
	private final Logger LOGGER = LoggerFactory.getLogger(QuestionExistsHandler.class);
	
	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		LOGGER.trace("Checking whether question exists or not. Question: " + request.getQuestion().get());
		if (!request.getQuestion().isPresent()) {
			LOGGER.trace("Question doesn't exists, return invalid question id");
			getResponse().setStatu("Error");
			getResponse().setInformation(new TeacherResponse("Invalid question id"));
			return new ResponseEntity<>(getResponse(), HttpStatus.NOT_FOUND);
		}
		LOGGER.trace("Question exists, go to the next cycle AnswerQuestionHandler");	
		return getNextHandler().handle(request);
	}
}
