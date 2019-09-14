package com.myProjects.soru_cozum.response;

import com.myProjects.soru_cozum.model.Student;

public class SignupResponse<T>{
	private String response;
	private T newRegister;
	public SignupResponse() {
		
	}
	public SignupResponse(String response) {
		this.response = response;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public T getNewRegister() {
		return newRegister;
	}
	public void setNewRegister(T newRegister) {
		this.newRegister = newRegister;
	}

}
