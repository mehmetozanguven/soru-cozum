package com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer;

import com.myProjects.soru_cozum.model.AnswerAudio;
import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.request.AnswerQuestionRequest;
import com.myProjects.soru_cozum.service.TeacherService;

public class TeacherUpdateAnswerRequest {
	private TeacherService teacherService;
	private AnswerQuestionRequest userRequest;
	private Teacher teacher;
	private AnswerImage oldAnswerImage;
	private AnswerAudio oldAnswerAudio;

	public TeacherUpdateAnswerRequest(TeacherService teacherService, AnswerQuestionRequest userRequest) {
		this.teacherService = teacherService;
		this.userRequest = userRequest;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public AnswerQuestionRequest getUserRequest() {
		return userRequest;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public AnswerImage getOldAnswerImage() {
		return oldAnswerImage;
	}

	public void setOldAnswerImage(AnswerImage oldAnswerImage) {
		this.oldAnswerImage = oldAnswerImage;
	}

	public AnswerAudio getOldAnswerAudio() {
		return oldAnswerAudio;
	}

	public void setOldAnswerAudio(AnswerAudio oldAnswerAudio) {
		this.oldAnswerAudio = oldAnswerAudio;
	}

}
