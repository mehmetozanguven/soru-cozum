package com.myProjects.soru_cozum.request;

public class NewRegisterRequestForStudent {
	
	private String username;
	private String name;
	private String password;
	private String surname;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStudentName() {
		return name;
	}
	public void setStudentName(String studentName) {
		this.name = studentName;
	}
	public String getStudentPassword() {
		return password;
	}
	public void setStudentPassword(String studentPassword) {
		this.password = studentPassword;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
}
