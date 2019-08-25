package com.myProjects.soru_cozum.chainPattern.signup.student;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.response.SignupResponse;

public class FindStudentByUsernameHandler extends StudentSignupAbstractHandler{

	@Override
	public ResponseEntity<?> handle(StudentSignupRequest request) {
		Optional<Student> student = request.getStudentService().findByUsername(request.getNewRegisterRequest().getUsername());
		if (student.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SignupResponse("Error", "Username already taken by another student"));
		else
			return getNextHandler().handle(request);
	}
	
	
}
