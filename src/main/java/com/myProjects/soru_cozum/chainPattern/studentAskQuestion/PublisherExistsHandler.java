package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.response.StudentAskQuestionResponse;


public class PublisherExistsHandler extends StudentAskQuestionAbstractHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublisherExistsHandler.class);

	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequest request) {
		LOGGER.debug("2. Check publisher exists or not");
		if (request.getPublisher().getName() == "nonce") {
			LOGGER.debug("Publisher not exists, that's means this is absolutely new question");
			LOGGER.debug("First, creating new publisher");
			Publisher newPublisher = request.getPublisherService().createNewPublisherFromRequest(request.getAddQuestionToStudentRequest().getPublisher());
			LOGGER.debug("Second, creating new question");
			Question newQuestion = createNewQuestion(request.getAddQuestionToStudentRequest().getQuestionUrl(), request.getAddQuestionToStudentRequest(), true, newPublisher, request.getQuestionService());
			// Add Question to Student
			LOGGER.debug("Adding Question to student Service");
			request.getStudentService().addQuestionToStudent(request.getStudent(), newQuestion);
			LOGGER.debug("Updating student database");
			request.getStudentService().updateStudent(request.getStudent());
			
			getResponse().setStatu("Success");
			getResponse().setInformation(new StudentAskQuestionResponse("Your question was sent to our teacher"));
			return new ResponseEntity<>(getResponse(), HttpStatus.OK);
		}
		else {
			LOGGER.debug("Publisher exists, then go to next cycle -check student ask that question before-");
			return getNextHandler().handle(request);
		}
	}
}
