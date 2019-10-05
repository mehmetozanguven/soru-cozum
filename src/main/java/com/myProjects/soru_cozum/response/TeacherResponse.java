package com.myProjects.soru_cozum.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myProjects.soru_cozum.response.service.TeacherAnswerAudioServiceResponse;
import com.myProjects.soru_cozum.response.service.TeacherAnswerImageServiceResponse;

public class TeacherResponse {
	@JsonAlias("response")
	private String response;
	
	@JsonAlias("answerImageDownloadJson")
	@JsonProperty("answerImageDownloadJson")
	private TeacherAnswerImageServiceResponse imageJson;
	
	@JsonAlias("answerAudioDownloadJson")
	@JsonProperty("answerAudioDownloadJson")
	private TeacherAnswerAudioServiceResponse audioJson;

	public TeacherResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public TeacherAnswerImageServiceResponse getImageJson() {
		return imageJson;
	}

	public void setImageJson(TeacherAnswerImageServiceResponse imageJson) {
		this.imageJson = imageJson;
	}

	public TeacherAnswerAudioServiceResponse getAudioJson() {
		return audioJson;
	}

	public void setAudioJson(TeacherAnswerAudioServiceResponse audioJson) {
		this.audioJson = audioJson;
	}

}
