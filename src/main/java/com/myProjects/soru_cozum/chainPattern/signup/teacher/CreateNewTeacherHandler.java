package com.myProjects.soru_cozum.chainPattern.signup.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myProjects.soru_cozum.model.Teacher;

public class CreateNewTeacherHandler extends TeacherSignupAbstractHandler{

	@Override
	public ResponseEntity<?> handle(TeacherSignupRequest request) {
		PasswordEncoder encoder = request.getPasswordEncoder();
		request.getNewRegisterRequest().setPassword(encoder.encode(request.getNewRegisterRequest().getPassword()));
		Teacher newTeacher = request.getTeacherService().createTeacherFromRequest(request.getNewRegisterRequest());
		
		request.getTeacherService().registerNewTeacher(newTeacher);
		
		return ResponseEntity.status(HttpStatus.OK).body(newTeacher);
	}
	
	
}
