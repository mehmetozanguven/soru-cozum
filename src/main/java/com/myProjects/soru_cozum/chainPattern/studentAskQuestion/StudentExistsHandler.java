package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.AddQuestionToStudentErrorResponse;

public class StudentExistsHandler extends StudentAskQuestionHandler {

	
	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequestHandler request) {
		if (request.getStudent().getName() == "nonce")
			return new ResponseEntity<>(new AddQuestionToStudentErrorResponse("Error", "Invalid Student ID"), HttpStatus.BAD_REQUEST);
		else
			return getNextHandler().handle(request);
	}
 
}
