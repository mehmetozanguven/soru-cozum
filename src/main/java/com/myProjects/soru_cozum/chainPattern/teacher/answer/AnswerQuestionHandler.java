package com.myProjects.soru_cozum.chainPattern.teacher.answer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.response.TeacherResponse;

public class AnswerQuestionHandler extends TeacherAnswerAbstractHandler{
	
	
	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		request.getTeacherService().resolveTeacherAccordingToAnswerQuestion(request.getTeacher(), request.getQuestion(), request.getAnswerQuestionRequest());
				
		request.getQuestion().addTeacherToQuestion(request.getTeacher());
		request.getQuestion().setAnswered(true);
		request.getQuestionService().updateQuestion(request.getQuestion());
		getResponse().setStatu("Success");
		getResponse().setInformation(new TeacherResponse("Your answer was sent"));
		return new ResponseEntity<>(getResponse(), HttpStatus.OK);
	}
}
