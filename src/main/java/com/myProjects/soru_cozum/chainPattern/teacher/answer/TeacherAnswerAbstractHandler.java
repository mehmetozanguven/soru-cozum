package com.myProjects.soru_cozum.chainPattern.teacher.answer;

import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.response.TeacherResponse;

public abstract class TeacherAnswerAbstractHandler {
	private TeacherAnswerAbstractHandler nextHandler;
	private GenericResponse<TeacherResponse> response;

	public TeacherAnswerAbstractHandler() {
		response = new GenericResponse<TeacherResponse>();
	}

	public void setNextHandler(TeacherAnswerAbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public TeacherAnswerAbstractHandler getNextHandler() {
		return this.nextHandler;
	}

	public GenericResponse<TeacherResponse> getResponse() {
		return response;
	}

	public abstract ResponseEntity<?> handle(TeacherAnswerRequest request);
}
