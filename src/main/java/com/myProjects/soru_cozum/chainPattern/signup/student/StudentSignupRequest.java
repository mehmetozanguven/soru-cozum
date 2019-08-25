package com.myProjects.soru_cozum.chainPattern.signup.student;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.myProjects.soru_cozum.request.NewRegisterRequestForStudent;
import com.myProjects.soru_cozum.service.StudentService;

public class StudentSignupRequest {
	private PasswordEncoder passwordEncoder;

	private NewRegisterRequestForStudent newRegisterRequest;
	
	private StudentService studentService;

	public StudentSignupRequest(PasswordEncoder passwordEncoder, NewRegisterRequestForStudent newRegisterRequest,
			StudentService studentService) {
		this.passwordEncoder = passwordEncoder;
		this.newRegisterRequest = newRegisterRequest;
		this.studentService = studentService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public NewRegisterRequestForStudent getNewRegisterRequest() {
		return newRegisterRequest;
	}

	public StudentService getStudentService() {
		return studentService;
	}
	
	

}
