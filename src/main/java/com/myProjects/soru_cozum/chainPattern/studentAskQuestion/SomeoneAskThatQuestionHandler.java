package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.response.AddQuestionToStudentErrorResponse;


public class SomeoneAskThatQuestionHandler extends StudentAskQuestionAbstractHandler{
	
	
	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequestHandler request) {
		Question isQuestionAskedBySomeone = request.getQuestionService().findQuestionByPageNumberQuestionNumberPublisher(
				request.getAddQuestionToStudentRequest().getPageNumber(), request.getAddQuestionToStudentRequest().getQuestionNumber(),
				request.getAddQuestionToStudentRequest().getPublisher());
		if (isQuestionAskedBySomeone.getId() != 0) {
			request.getStudentService().addQuestionToStudent(request.getStudent(), isQuestionAskedBySomeone);
			request.getStudentService().updateStudent(request.getStudent());
			return ResponseEntity.ok()
					.body(new AddQuestionToStudentErrorResponse("Success", "Question already have been asked by someone, we added to your list"));
		}else 
			return getNextHandler().handle(request);
	}
}
