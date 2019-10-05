package com.myProjects.soru_cozum.model.json;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myProjects.soru_cozum.model.json.teacher.TeacherAnswerAudioJSON;
import com.myProjects.soru_cozum.model.json.teacher.TeacherAnswerImageJSON;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnsweredQuestionJSON {
	
	@JsonAlias("answerImageDownloadJSON")
	private TeacherAnswerImageJSON imageJSON;
	
	@JsonAlias("answerAudioDownloadJSON")
	private TeacherAnswerAudioJSON audioJSON;
	
	@JsonAlias("studentList")
	private Set<StudentJSON> studentList;

	public AnsweredQuestionJSON(TeacherAnswerImageJSON imageJSON, TeacherAnswerAudioJSON audioJSON,
			Set<StudentJSON> studentList) {
		this.imageJSON = imageJSON;
		this.audioJSON = audioJSON;
		this.studentList = studentList;
	}

	public TeacherAnswerImageJSON getImageJSON() {
		return imageJSON;
	}

	public void setImageJSON(TeacherAnswerImageJSON imageJSON) {
		this.imageJSON = imageJSON;
	}

	public TeacherAnswerAudioJSON getAudioJSON() {
		return audioJSON;
	}

	public void setAudioJSON(TeacherAnswerAudioJSON audioJSON) {
		this.audioJSON = audioJSON;
	}

	public Set<StudentJSON> getStudentList() {
		return studentList;
	}

	public void setStudentList(Set<StudentJSON> studentList) {
		this.studentList = studentList;
	}
	
	
		
}
