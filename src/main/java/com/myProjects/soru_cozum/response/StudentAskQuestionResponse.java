package com.myProjects.soru_cozum.response;

public class StudentAskQuestionResponse {
	private String response;
	private StudentQuestionUploadResponse imageDownloadJson;
	
	public StudentAskQuestionResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public StudentQuestionUploadResponse getImageDownloadJson() {
		return imageDownloadJson;
	}

	public void setImageDownloadJson(StudentQuestionUploadResponse imageDownloadJson) {
		this.imageDownloadJson = imageDownloadJson;
	}
	
	
}
