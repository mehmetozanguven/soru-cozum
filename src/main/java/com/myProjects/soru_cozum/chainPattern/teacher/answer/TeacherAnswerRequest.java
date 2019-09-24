package com.myProjects.soru_cozum.chainPattern.teacher.answer;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.request.AnswerQuestionRequest;
import com.myProjects.soru_cozum.service.FileStorageService;
import com.myProjects.soru_cozum.service.QuestionService;
import com.myProjects.soru_cozum.service.TeacherService;

public class TeacherAnswerRequest {

	private QuestionService questionService;
	private TeacherService teacherService;
	private FileStorageService fileStorageService;
	private Optional<Teacher> teacher;
	private Optional<Question> question;
	private MultipartFile imageFile;
	private MultipartFile answerAudioFile;

	public TeacherAnswerRequest(QuestionService questionService, TeacherService teacherService,
			Optional<Teacher> teacher, Optional<Question> question, FileStorageService fileStorageService) {
		this.questionService = questionService;
		this.teacherService = teacherService;
		this.fileStorageService = fileStorageService;
		this.teacher = teacher;
		this.question = question;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public FileStorageService getFileStorageService() {
		return fileStorageService;
	}

	public Optional<Teacher> getTeacher() {
		return teacher;
	}

	public void setTeacher(Optional<Teacher> teacher) {
		this.teacher = teacher;
	}

	public Optional<Question> getQuestion() {
		return question;
	}

	public void setQuestion(Optional<Question> question) {
		this.question = question;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public MultipartFile getAnswerAudioFile() {
		return answerAudioFile;
	}

	public void setAnswerAudioFile(MultipartFile answerAudioFile) {
		this.answerAudioFile = answerAudioFile;
	}

}
