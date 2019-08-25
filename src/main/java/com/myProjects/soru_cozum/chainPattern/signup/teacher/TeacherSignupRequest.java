package com.myProjects.soru_cozum.chainPattern.signup.teacher;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.myProjects.soru_cozum.request.NewRegisterRequestForTeacher;
import com.myProjects.soru_cozum.service.TeacherService;

public class TeacherSignupRequest {
	private PasswordEncoder passwordEncoder;

	private NewRegisterRequestForTeacher newRegisterRequest;

	private TeacherService teacherService;
	

	public TeacherSignupRequest(PasswordEncoder passwordEncoder, NewRegisterRequestForTeacher newRegisterRequest,
			TeacherService teacherService) {
		this.passwordEncoder = passwordEncoder;
		this.newRegisterRequest = newRegisterRequest;
		this.teacherService = teacherService;
	}
	
	


	public TeacherService getTeacherService() {
		return teacherService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public NewRegisterRequestForTeacher getNewRegisterRequest() {
		return newRegisterRequest;
	}

}
