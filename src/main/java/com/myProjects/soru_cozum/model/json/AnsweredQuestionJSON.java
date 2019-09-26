package com.myProjects.soru_cozum.model.json;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myProjects.soru_cozum.model.json.teacher.TeacherAnswerAudioJSON;
import com.myProjects.soru_cozum.model.json.teacher.TeacherAnswerImageJSON;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnsweredQuestionJSON {
	
	@JsonAlias("imageDownloadJSON")
	private TeacherAnswerImageJSON imageJSON;
	
	@JsonAlias("audioDownloadJSON")
	private TeacherAnswerAudioJSON audioJSON;
	
	@JsonAlias("studentList")
	private Set<StudentJSON> studentList;

	public AnsweredQuestionJSON(TeacherAnswerImageJSON imageJSON, TeacherAnswerAudioJSON audioJSON,
			Set<StudentJSON> studentList) {
		this.imageJSON = imageJSON;
		this.audioJSON = audioJSON;
		this.studentList = studentList;
	}
	
	
		
}
