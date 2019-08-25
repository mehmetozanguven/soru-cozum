package com.myProjects.soru_cozum.chainPattern.signup.teacher;

import org.springframework.http.ResponseEntity;

public abstract class TeacherSignupAbstractHandler {
	private TeacherSignupAbstractHandler nextHandler;

	public TeacherSignupAbstractHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(TeacherSignupAbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	public abstract ResponseEntity<?> handle(TeacherSignupRequest request);
}
