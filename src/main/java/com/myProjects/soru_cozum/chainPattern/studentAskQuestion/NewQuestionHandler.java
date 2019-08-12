package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.springframework.http.ResponseEntity;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.response.AddQuestionToStudentErrorResponse;


public class NewQuestionHandler extends StudentAskQuestionAbstractHandler {

	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequestHandler request) {
		// After all, this is new question (absolutely!!!)
		Question newQuestion = createNewQuestion(request.getAddQuestionToStudentRequest(), true,
				request.getPublisher(), request.getQuestionService());
		// Add Question to Student
		request.getStudentService().addQuestionToStudent(request.getStudent(), newQuestion);
		request.getStudentService().updateStudent(request.getStudent());

		return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "QuestionAdded"));

	}

	
}
