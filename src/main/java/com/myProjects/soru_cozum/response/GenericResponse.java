package com.myProjects.soru_cozum.response;

public class GenericResponse<T> {
	private String statu;
	private T information;
	
	public GenericResponse() {
	}
	public GenericResponse(String statu) {
		this.statu = statu;
	}
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public T getInformation() {
		return information;
	}
	public void setInformation(T t) {
		this.information = t;
	}	
}
