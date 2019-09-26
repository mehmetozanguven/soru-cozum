package com.myProjects.soru_cozum.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.myProjects.soru_cozum.response.service.TeacherAnswerAudioServiceResponse;
import com.myProjects.soru_cozum.response.service.TeacherAnswerImageServiceResponse;

public class TeacherResponse {
	@JsonAlias("response")
	private String response;
	@JsonAlias("imageDownloadJson")
	private TeacherAnswerImageServiceResponse imageJson;
	@JsonAlias("audioDownloadJson")
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
