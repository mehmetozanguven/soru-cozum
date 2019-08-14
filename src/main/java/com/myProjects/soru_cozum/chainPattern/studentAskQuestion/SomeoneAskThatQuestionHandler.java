package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.response.AddQuestionToStudentErrorResponse;


public class SomeoneAskThatQuestionHandler extends StudentAskQuestionAbstractHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SomeoneAskThatQuestionHandler.class);
	
	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequestHandler request) {
		LOGGER.debug("4. Checking another student ask the question or not");
		LOGGER.debug("\tQuestion properties:");
		LOGGER.debug("\tStudent Id: " + request.getStudent().getId());
		LOGGER.debug("\tPublisher Id: " + request.getPublisher().getId());
		LOGGER.debug("\tPage number of question: " + request.getAddQuestionToStudentRequest().getPageNumber());
		LOGGER.debug("\tQuestion number of question: " + request.getAddQuestionToStudentRequest().getQuestionNumber());
		
		Question isQuestionAskedBySomeone = request.getQuestionService().findQuestionByPageNumberQuestionNumberPublisher(
				request.getAddQuestionToStudentRequest().getPageNumber(), request.getAddQuestionToStudentRequest().getQuestionNumber(),
				request.getAddQuestionToStudentRequest().getPublisher());
		if (isQuestionAskedBySomeone.getId() != 0) {
			LOGGER.debug("Another student ask that question, then add only question to student list");
			LOGGER.debug("Adding Question to the Student");
			request.getStudentService().addQuestionToStudent(request.getStudent(), isQuestionAskedBySomeone);
			LOGGER.debug("Update student without adding new Question !!!!");
			request.getStudentService().addQuestionToStudentWithoutCreatingNewQuestion(isQuestionAskedBySomeone, request.getStudent());
			return ResponseEntity.ok()
					.body(new AddQuestionToStudentErrorResponse("Success", "Question already have been asked by someone, we added to your list"));
		}else {
			LOGGER.debug("No one asked that question before, then go to next cycle -New Question-");
			return getNextHandler().handle(request);
		}
			
	}
}
