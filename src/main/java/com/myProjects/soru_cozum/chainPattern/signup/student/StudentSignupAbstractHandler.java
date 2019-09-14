package com.myProjects.soru_cozum.chainPattern.signup.student;

import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.response.SignupResponse;

public abstract class StudentSignupAbstractHandler {
	private StudentSignupAbstractHandler nextHandler;
	private GenericResponse<SignupResponse> response;

	public StudentSignupAbstractHandler() {
		response = new GenericResponse<SignupResponse>();
	}

	public StudentSignupAbstractHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(StudentSignupAbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public GenericResponse<SignupResponse> getResponse() {
		return response;
	}

	public abstract ResponseEntity<?> handle(StudentSignupRequest request);
}
