package com.myProjects.soru_cozum.response;

public class SignupResponse {
	private String statu;
	private String information;
	
	public SignupResponse() {
		
	}
	public SignupResponse(String statu, String information) {
		super();
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
