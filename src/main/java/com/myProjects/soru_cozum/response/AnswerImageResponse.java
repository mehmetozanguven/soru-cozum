package com.myProjects.soru_cozum.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerImageResponse {
	
	@JsonAlias("imageFolder")
	private String folderName;
	
	@JsonAlias("teacherId")
	private int teacherId;
	
	@JsonAlias("questionId")
	private int questionId;

	public AnswerImageResponse(String folderName, int teacherId, int questionId) {
		this.folderName = folderName;
		this.teacherId = teacherId;
		this.questionId = questionId;
	}

	public String getFolderName() {
		return folderName;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public int getQuestionId() {
		return questionId;
	}
	
	
}	
