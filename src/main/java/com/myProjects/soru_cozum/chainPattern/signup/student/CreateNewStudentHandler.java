package com.myProjects.soru_cozum.chainPattern.signup.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.response.SignupResponse;

public class CreateNewStudentHandler extends StudentSignupAbstractHandler{
	private final Logger LOGGER = LoggerFactory.getLogger(CreateNewStudentHandler.class);
	
	@Override
	public ResponseEntity<?> handle(StudentSignupRequest request) {
		PasswordEncoder encoder = request.getPasswordEncoder();
		request.getNewRegisterRequest().setPassword(encoder.encode(request.getNewRegisterRequest().getPassword()));
		String name = request.getNewRegisterRequest().getName();
		String password = request.getNewRegisterRequest().getPassword();
		String username = request.getNewRegisterRequest().getUsername();
		String surname = request.getNewRegisterRequest().getSurname();
		
		Student newStudent = request.getStudentService().createNewStudent(name, password, username, surname);
		
		if (request.getStudentDetails() == null)
			LOGGER.debug("Student details null. Won't be set");
		else
			newStudent.setStudentDetails(request.getStudentDetails());
		
		Long studentId = request.getStudentService().registerNewStudent(newStudent);
		if (studentId == 0 || studentId == null) {
			getResponse().setStatu("Error");
			getResponse().setInformation(new SignupResponse<Student>("Server Error"));
			return new ResponseEntity<>(getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		getResponse().setStatu("Error");
		SignupResponse<Student> signupResponse = new SignupResponse<Student>("New Student Created");
		signupResponse.setNewRegister(newStudent);
		getResponse().setInformation(signupResponse);
		return new ResponseEntity<>(getResponse(), HttpStatus.OK);
	}
	
}
