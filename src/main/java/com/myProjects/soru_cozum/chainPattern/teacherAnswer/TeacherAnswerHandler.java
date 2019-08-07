package com.myProjects.soru_cozum.chainPattern.teacherAnswer;

import org.springframework.http.ResponseEntity;

public abstract class TeacherAnswerHandler {
	private TeacherAnswerHandler nextHandler;
	
	public void setNextHandler(TeacherAnswerHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	public TeacherAnswerHandler getNextHandler() {
		return this.nextHandler;
	}
	
	public abstract ResponseEntity<?> handle(TeacherAnswerRequest request);
}
