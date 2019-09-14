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
	
	@JsonAlias("ImageByte")
//	@JsonIgnore
	private byte[] imageByte;
	
	@JsonAlias("AudioByte")
//	@JsonIgnore
	private byte[] audioByte;
	
	// temporal, delete when unnecessary
/*	@JsonAlias("ImageByte")
	private String imageFilePath;
	
	@JsonAlias("AudioByte")
	private String audioFilePath;*/

	
	
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

	
/*
	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public String getAudioFilePath() {
		return audioFilePath;
	}

	public void setAudioFilePath(String audioFilePath) {
		this.audioFilePath = audioFilePath;
	}
	*/
	
}
