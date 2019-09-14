package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.response.StudentAskQuestionResponse;

public class StudentAskThatQuestionHandler extends StudentAskQuestionAbstractHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentAskThatQuestionHandler.class);
	
	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequest request) {
		LOGGER.debug("3. Checking student ask that question before or not");
		LOGGER.debug("\tQuestion properties:");
		LOGGER.debug("\tStudent Id: " + request.getStudent().getId());
		LOGGER.debug("\tPublisher Id: " + request.getPublisher().getId());
		LOGGER.debug("\tPage number of question: " + request.getAddQuestionToStudentRequest().getPageNumber());
		LOGGER.debug("\tQuestion number of question: " + request.getAddQuestionToStudentRequest().getQuestionNumber());
		Question isStudentAskedThatQuestionBefore = request.getStudentService().isStudentAskedThatQuestionBefore(request.getStudent(), request.getPublisher(),
				request.getAddQuestionToStudentRequest().getPageNumber(), request.getAddQuestionToStudentRequest().getQuestionNumber());
		
		if (isStudentAskedThatQuestionBefore.getId() != 0) {
			getResponse().setStatu("Success");
			LOGGER.debug("Student asked that question before, checking whether it was answered by our teacher");
			if (isStudentAskedThatQuestionBefore.isAnswered()) {
				LOGGER.debug("Asked question was answered by our teacher");
				getResponse().setInformation(new StudentAskQuestionResponse("You asked that question before and it was answered by a teacher check your answer list"));
			}
			else {
				LOGGER.debug("Asked question wasn't answered by our teacher");
				getResponse().setInformation(new StudentAskQuestionResponse("You asked that question before, answer waiting"));
			}
			return new ResponseEntity<>(getResponse(), HttpStatus.OK);
		}else {
			LOGGER.debug("Student didn't asked that question before, next cycle -Check another student ask the question-");
			return getNextHandler().handle(request);
		}
			
	}
}
