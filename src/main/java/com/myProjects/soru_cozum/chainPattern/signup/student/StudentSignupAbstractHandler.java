package com.myProjects.soru_cozum.chainPattern.signup.student;


import org.springframework.http.ResponseEntity;

public abstract class StudentSignupAbstractHandler {
	private StudentSignupAbstractHandler nextHandler;

	public StudentSignupAbstractHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(StudentSignupAbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	public abstract ResponseEntity<?> handle(StudentSignupRequest request);
}
