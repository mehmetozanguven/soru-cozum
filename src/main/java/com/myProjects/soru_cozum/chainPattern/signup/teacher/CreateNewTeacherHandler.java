package com.myProjects.soru_cozum.chainPattern.signup.teacher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myProjects.soru_cozum.model.Teacher;

public class CreateNewTeacherHandler extends TeacherSignupAbstractHandler{
	
	private final Logger LOGGER = LoggerFactory.getLogger(CreateNewTeacherHandler.class);
	
	@Override
	public ResponseEntity<?> handle(TeacherSignupRequest request) {
		PasswordEncoder encoder = request.getPasswordEncoder();
		request.getNewRegisterRequest().setPassword(encoder.encode(request.getNewRegisterRequest().getPassword()));
		
		String name = request.getNewRegisterRequest().getName();
		String password = request.getNewRegisterRequest().getPassword();
		String username = request.getNewRegisterRequest().getUsername();
		String surname = request.getNewRegisterRequest().getSurname();
		
		Teacher newTeacher = request.getTeacherService().createNewTeacher(name, password, username, surname);
		
		if (request.getTeacherDetails() == null)
			LOGGER.debug("Request has no body about details. Won't be set");
		else
			newTeacher.setTeacherDetails(request.getTeacherDetails());
		
		request.getTeacherService().registerNewTeacher(newTeacher);
		
		return ResponseEntity.status(HttpStatus.OK).body(newTeacher);
	}
	
	
}
