package com.myProjects.soru_cozum.chainPattern.signup.teacher;

import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.response.SignupResponse;

public abstract class TeacherSignupAbstractHandler {
	private TeacherSignupAbstractHandler nextHandler;
	@SuppressWarnings("rawtypes")
	private GenericResponse<SignupResponse> response;

	@SuppressWarnings("rawtypes")
	public TeacherSignupAbstractHandler() {
		response = new GenericResponse<SignupResponse>();
	}

	public TeacherSignupAbstractHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(TeacherSignupAbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	@SuppressWarnings("rawtypes")
	public GenericResponse<SignupResponse> getResponse() {
		return response;
	}

	public abstract ResponseEntity<?> handle(TeacherSignupRequest request);
}
