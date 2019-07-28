package com.myProjects.soru_cozum.exceptionResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class StudentNotFoundException{
	private String statu;
	private String info;
	public StudentNotFoundException(String statu, String info) {
		super();
		this.statu = statu;
		this.info = info;
	}
	
	
}
