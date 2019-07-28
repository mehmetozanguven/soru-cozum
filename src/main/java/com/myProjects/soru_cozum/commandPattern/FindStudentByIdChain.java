package com.myProjects.soru_cozum.commandPattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.service.StudentService;

public class FindStudentByIdChain implements Chain<Student>{
	private Student student;
	private Chain nextInChain;
	
	@Autowired
	private StudentService studentService;
	
	public FindStudentByIdChain(Student student) {
		this.student = student;
	}
	
	@Override
	public void setNextChain(Chain nextChain) {
		nextInChain = nextChain;
	}
	
	
	@Override
	public Student getResult() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void process() {
		
	}
	
}
