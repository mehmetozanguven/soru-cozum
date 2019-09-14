package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.response.StudentAskQuestionResponse;


public class NewQuestionHandler extends StudentAskQuestionAbstractHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(NewQuestionHandler.class);
	
	
	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequest request) {
		LOGGER.debug("5. This is definitely new question");
		LOGGER.debug("Creating new question");
		Question newQuestion = createNewQuestion(request.getAddQuestionToStudentRequest().getQuestionUrl(), request.getAddQuestionToStudentRequest(), true,
				request.getPublisher(), request.getQuestionService());
		LOGGER.debug("Adding Question to the Student");
		request.getStudentService().addQuestionToStudent(request.getStudent(), newQuestion);
		LOGGER.debug("Update the student with adding new question into the database");
		request.getStudentService().updateStudent(request.getStudent());
		
		getResponse().setStatu("Success");
		getResponse().setInformation(new StudentAskQuestionResponse("QuestionAdded"));
		return new ResponseEntity<>(getResponse(), HttpStatus.OK);
	}
	
	
	
}
