package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.response.StudentAskQuestionResponse;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;

public class StudentAskThatQuestionHandler extends StudentAskQuestionAbstractHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentAskThatQuestionHandler.class);
	
	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequest request) {
		LOGGER.debug("3. Checking student ask that question before or not");
		Optional<Question> isStudentAskedThatQuestionBefore = null;

		isStudentAskedThatQuestionBefore = request.getStudentService().isStudentAskedThatQuestionBefore(
				request.getStudent().get(), request.getPublisher().get(),
				request.getPageNumber(),
				request.getQuestionNumber(), request.getQuestionCategory(),
				request.getQuestionSubCategory());
		
		StudentQuestionUploadResponse imageDownloadJson = prepareQuestionDownloadJson(request);
		
		StudentAskQuestionResponse response;
		if (isStudentAskedThatQuestionBefore.isPresent()) {
			getResponse().setStatu("Success");
			LOGGER.debug("Student asked that question before, checking whether it was answered by our teacher");
			if (isStudentAskedThatQuestionBefore.get().isAnswered()) {
				LOGGER.debug("Asked question was answered by our teacher");
				response = new StudentAskQuestionResponse("You asked that question before and it was answered by a teacher check your answer list");
				response.setImageDownloadJson(imageDownloadJson);
				getResponse().setInformation(response);
			}
			else {
				LOGGER.debug("Asked question wasn't answered by our teacher");
				response = new StudentAskQuestionResponse("You asked that question before, answer waiting");
				response.setImageDownloadJson(imageDownloadJson);
				getResponse().setInformation(response);
			}
			return new ResponseEntity<>(getResponse(), HttpStatus.OK);
		}else {
			LOGGER.debug("Student didn't asked that question before, next cycle -Check another student ask the question-");
			return getNextHandler().handle(request);
		}
			
	}
}
