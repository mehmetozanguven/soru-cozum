package com.myProjects.soru_cozum.chainPattern.teacherAnswer;

import org.springframework.http.ResponseEntity;

public abstract class TeacherAnswerAbstractHandler {
	private TeacherAnswerAbstractHandler nextHandler;
	
	public void setNextHandler(TeacherAnswerAbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	public TeacherAnswerAbstractHandler getNextHandler() {
		return this.nextHandler;
	}
	
	public abstract ResponseEntity<?> handle(TeacherAnswerRequest request);
}
