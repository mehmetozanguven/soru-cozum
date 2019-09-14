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
			getResponse().setStatu("Error");
			getResponse().setInformation(new SignupResponse<Teacher>("This username already taken by another teacher"));
			return new ResponseEntity<>(getResponse(), HttpStatus.OK);
		} else
			return getNextHandler().handle(request);
	}

}
