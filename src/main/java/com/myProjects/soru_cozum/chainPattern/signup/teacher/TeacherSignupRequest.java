package com.myProjects.soru_cozum.chainPattern.signup.teacher;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.myProjects.soru_cozum.model.TeacherDetails;
import com.myProjects.soru_cozum.request.GenericSignupRequest;
import com.myProjects.soru_cozum.service.TeacherService;

public class TeacherSignupRequest {
	private PasswordEncoder passwordEncoder;

	private GenericSignupRequest newRegisterRequest;

	private TeacherService teacherService;
	
	private TeacherDetails teacherDetails;

	public TeacherSignupRequest(PasswordEncoder passwordEncoder, GenericSignupRequest newRegisterRequest,
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

	public GenericSignupRequest getNewRegisterRequest() {
		return newRegisterRequest;
	}

	public TeacherDetails getTeacherDetails() {
		return teacherDetails;
	}

	public void setTeacherDetails(TeacherDetails teacherDetails) {
		this.teacherDetails = teacherDetails;
	}
	
}
