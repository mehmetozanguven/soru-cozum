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
		LOGGER.debug("5. This is definitely new question with existing publisher");
		LOGGER.debug("Creating new question");
		
		if (request.getUploadResponse() == null) {
			getResponse().setStatu("Failed. Couldn't upload the file");
		}
		
		int pageNumber = request.getPageNumber();
		int questionNumber = request.getQuestionNumber();
		Question newQuestion = createNewQuestion(pageNumber, questionNumber,
												 request.getQuestionCategory(), request.getQuestionSubCategory(),
												 false,
												 request.getPublisher().get(),
												 request.getQuestionService());
		request.getPublisher().get().addQuestion(newQuestion);
		
		LOGGER.debug("Adding Question to the Student");
		request.getStudentService().addQuestionToStudent(request.getStudent().get(), newQuestion);
		LOGGER.debug("Update the student with adding new question into the database");
		request.getStudentService().updateStudent(request.getStudent().get());
		
		StudentAskQuestionResponse response = new StudentAskQuestionResponse("QuestionAdded");
		response.setImageDownloadJson(request.getUploadResponse());
		getResponse().setStatu("Success");
		getResponse().setInformation(response);
		return new ResponseEntity<>(getResponse(), HttpStatus.OK);
	}
	
	
	
}
