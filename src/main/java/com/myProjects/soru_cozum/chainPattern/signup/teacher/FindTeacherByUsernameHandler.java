package com.myProjects.soru_cozum.chainPattern.signup.teacher;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.response.SignupResponse;

public class FindTeacherByUsernameHandler extends TeacherSignupAbstractHandler {

	@Override
	public ResponseEntity<?> handle(TeacherSignupRequest request) {
		Optional<Teacher> teacherFromDatabase = request.getTeacherService()
				.findTeacherByUsername(request.getNewRegisterRequest().getUsername());
		if (teacherFromDatabase.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new SignupResponse("Error", "This username already taken by another teacher"));
		} else
			return getNextHandler().handle(request);

	}

}
