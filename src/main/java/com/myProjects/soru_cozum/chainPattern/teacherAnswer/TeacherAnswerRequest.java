package com.myProjects.soru_cozum.chainPattern.teacherAnswer;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.request.AnswerQuestionRequest;
import com.myProjects.soru_cozum.service.QuestionService;
import com.myProjects.soru_cozum.service.TeacherService;

public class TeacherAnswerRequest {
	
	private QuestionService questionService;
	private TeacherService teacherService;
	private Teacher teacher;
	private Question question;
	private AnswerQuestionRequest answerQuestionRequest;
	
	public TeacherAnswerRequest(QuestionService questionService, TeacherService teacherService, Teacher teacher,
			Question question, AnswerQuestionRequest answerQuestionRequest) {
		this.questionService = questionService;
		this.teacherService = teacherService;
		this.teacher = teacher;
		this.question = question;
		this.answerQuestionRequest = answerQuestionRequest;
	}

	public TeacherAnswerRequest(QuestionService questionService, TeacherService teacherService, Teacher teacher, Question question) {
		this.questionService = questionService;
		this.teacherService = teacherService;
		this.teacher = teacher;
		this.question = question;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Question getQuestion() {
		return question;
	}

	public AnswerQuestionRequest getAnswerQuestionRequest() {
		return answerQuestionRequest;
	}
	
	
	
	
}
