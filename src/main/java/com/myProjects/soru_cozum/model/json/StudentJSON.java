package com.myProjects.soru_cozum.model.json;

import com.fasterxml.jackson.annotation.JsonAlias;

public class StudentJSON {
	@JsonAlias("studentName")
	private String name;
	@JsonAlias("studentSurname")
	private String surname;
	
	public StudentJSON(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "StudentJSON [name=" + name + ", surname=" + surname + "]";
	}
	
	
	
}
