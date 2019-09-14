package com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer;

import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.response.TeacherResponse;

public abstract class TeacherUpdateAnswerAbstactHandler {
	private TeacherUpdateAnswerAbstactHandler nextHandler;
	private GenericResponse<TeacherResponse> response;

	public TeacherUpdateAnswerAbstactHandler() {
		response = new GenericResponse<TeacherResponse>();
	}

	public TeacherUpdateAnswerAbstactHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(TeacherUpdateAnswerAbstactHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public GenericResponse<TeacherResponse> getResponse() {
		return response;
	}

	public abstract ResponseEntity<?> handle(TeacherUpdateAnswerRequest request);

}
