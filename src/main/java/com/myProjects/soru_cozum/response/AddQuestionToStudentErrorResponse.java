package com.myProjects.soru_cozum.response;

public class AddQuestionToStudentErrorResponse {
	
	private String statu;
	private String information;
	public AddQuestionToStudentErrorResponse(String statu, String information) {
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
