package com.myProjects.soru_cozum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.request.NewRegisterRequest;
import com.myProjects.soru_cozum.service.StudentService;

@RestController()
@RequestMapping("/signup")
public class SingupController {
	@Autowired
	private StudentService studentService;
	
	@PostMapping
	public ResponseEntity<?> registerNewStudent(@RequestBody NewRegisterRequest newRegisterRequest){
		Student newStudent = studentService.createStudentFromRequest(newRegisterRequest);
		studentService.registerNewStudent(newStudent);
		
		return ResponseEntity.ok(newStudent);
	}
	
	
	
}
