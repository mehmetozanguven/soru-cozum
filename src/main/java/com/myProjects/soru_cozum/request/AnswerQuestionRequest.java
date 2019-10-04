package com.myProjects.soru_cozum.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerQuestionRequest {
	
	@JsonAlias("TeacherId")
	private Long teacherId;
	
	@JsonAlias("QuestionId")
	private Long questionId;
	
	@JsonAlias("ImageFile")
//	@JsonIgnore
	private byte[] imageByte;
	
	@JsonAlias("AudioByte")
//	@JsonIgnore
	private byte[] audioByte;

	
	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public byte[] getImageByte() {
		return imageByte;
	}

	public void setImageByte(byte[] imageByte) {
		this.imageByte = imageByte;
	}

	public byte[] getAudioByte() {
		return audioByte;
	}

	public void setAudioByte(byte[] audioByte) {
		this.audioByte = audioByte;
	}

	

	
}
