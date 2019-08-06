package com.myProjects.soru_cozum.response;

public class AnswerQuestionResponse {
	
	private String statu;
	private String information;
	
	
	
	public AnswerQuestionResponse(String statu, String information) {
		this.statu = statu;
		this.information = information;
	}
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	
	
}
