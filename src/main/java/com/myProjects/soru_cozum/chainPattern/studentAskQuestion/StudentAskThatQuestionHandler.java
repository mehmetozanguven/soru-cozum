package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.response.AddQuestionToStudentErrorResponse;

public class StudentAskThatQuestionHandler extends StudentAskQuestionHandler{

	
	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequestHandler request) {
		Question isStudentAskedThatQuestionBefore = request.getStudentService().isStudentAskedThatQuestionBefore(request.getStudent(), request.getPublisher(),
				request.getAddQuestionToStudentRequest().getPageNumber(), request.getAddQuestionToStudentRequest().getQuestionNumber());
		
		if (isStudentAskedThatQuestionBefore.getId() != 0) {
			if (isStudentAskedThatQuestionBefore.isAnswered())
				return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "You asked that question before and it was answered by a teacher check your answer list"));
			else
				return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "You asked that question before, answer waiting"));
		}else
			return getNextHandler().handle(request);
	}
}
