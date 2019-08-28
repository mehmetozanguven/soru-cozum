package com.myProjects.soru_cozum.chainPattern.signup.student;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.myProjects.soru_cozum.model.StudentDetails;
import com.myProjects.soru_cozum.request.GenericSignupRequest;
import com.myProjects.soru_cozum.service.StudentService;

public class StudentSignupRequest {
	private PasswordEncoder passwordEncoder;

	private GenericSignupRequest newRegisterRequest;

	private StudentService studentService;

	private StudentDetails studentDetails;

	public StudentSignupRequest(PasswordEncoder passwordEncoder, GenericSignupRequest newRegisterRequest,
			StudentService studentService) {
		this.passwordEncoder = passwordEncoder;
		this.newRegisterRequest = newRegisterRequest;
		this.studentService = studentService;
	}

	public StudentDetails getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public GenericSignupRequest getNewRegisterRequest() {
		return newRegisterRequest;
	}

	public StudentService getStudentService() {
		return studentService;
	}

}
