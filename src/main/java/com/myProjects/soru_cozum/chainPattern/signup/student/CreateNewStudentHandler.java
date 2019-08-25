package com.myProjects.soru_cozum.chainPattern.signup.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.response.SignupResponse;

public class CreateNewStudentHandler extends StudentSignupAbstractHandler{

	@Override
	public ResponseEntity<?> handle(StudentSignupRequest request) {
		PasswordEncoder encoder = request.getPasswordEncoder();
		request.getNewRegisterRequest().setStudentPassword(encoder.encode(request.getNewRegisterRequest().getStudentPassword()));
		Student newStudent = request.getStudentService().createStudentFromRequest(request.getNewRegisterRequest());
		Long studentId = request.getStudentService().registerNewStudent(newStudent);
		if (studentId == 0 || studentId == null)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SignupResponse("Error", "Server Error"));
		return ResponseEntity.status(HttpStatus.OK).body(newStudent);
	}
	
}
