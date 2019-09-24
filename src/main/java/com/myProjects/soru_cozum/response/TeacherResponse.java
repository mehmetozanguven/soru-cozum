package com.myProjects.soru_cozum.response;

import com.myProjects.soru_cozum.response.service.TeacherAnswerImageServiceResponse;

public class TeacherResponse {

	private String response;
	private TeacherAnswerImageServiceResponse imageJson;

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

}
