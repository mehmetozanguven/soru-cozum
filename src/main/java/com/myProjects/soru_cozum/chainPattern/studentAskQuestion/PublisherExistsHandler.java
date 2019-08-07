package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;


import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.response.AddQuestionToStudentErrorResponse;


public class PublisherExistsHandler extends StudentAskQuestionHandler {

	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequestHandler request) {
		if (request.getPublisher().getName() == "nonce") {
			Publisher newPublisher = request.getPublisherService().createNewPublisherFromRequest(request.getAddQuestionToStudentRequest().getPublisher());
			Question newQuestion = createNewQuestion(request.getAddQuestionToStudentRequest(), true, newPublisher, request.getQuestionService());
			// Add Question to Student
			request.getStudentService().addQuestionToStudent(request.getStudent(), newQuestion);
			request.getStudentService().updateStudent(request.getStudent());

			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "Your question was sent to our teacher"));		
		}
		else
			return getNextHandler().handle(request);
	}
}
