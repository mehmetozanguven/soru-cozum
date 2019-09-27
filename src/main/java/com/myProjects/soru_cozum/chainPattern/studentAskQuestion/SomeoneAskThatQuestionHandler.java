package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.response.StudentAskQuestionResponse;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;


public class SomeoneAskThatQuestionHandler extends StudentAskQuestionAbstractHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SomeoneAskThatQuestionHandler.class);
	
	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequest request) {
		LOGGER.debug("4. Checking another student ask the question or not");
			
		Optional<Question> isQuestionAskedBySomeone = request.getQuestionService()
				.findQuestionByPageNumber_QuestionNumber_Publisher(request.getPageNumber(),
						request.getQuestionNumber(), request.getPublisher().get(),
						request.getQuestionCategory(), request.getQuestionSubCategory());
		
		StudentQuestionUploadResponse imageDownloadJson = prepareQuestionDownloadJson(request);
		StudentAskQuestionResponse response = new StudentAskQuestionResponse("Question already have been asked by someone, we added to your list");
		response.setImageDownloadJson(imageDownloadJson);
		if (isQuestionAskedBySomeone.isPresent()) {
			LOGGER.debug("Another student ask that question, then add only question to student list");
			LOGGER.debug("Adding Question to the Student");
			LOGGER.debug("Update student without adding new Question !!!!");
			request.getStudentService().addQuestionToStudentWithoutCreatingNewQuestion(isQuestionAskedBySomeone.get(), request.getStudent().get());
			
			getResponse().setStatu("Success");
			getResponse().setInformation(response);
			return new ResponseEntity<>(getResponse(), HttpStatus.OK);

		}else {
			LOGGER.debug("No one asked that question before, then go to next cycle -File Storage Handler-");
			return getNextHandler().handle(request);
		}			
	}
}
