package com.myProjects.soru_cozum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.request.NewRegisterRequest;
import com.myProjects.soru_cozum.response.SignupResponse;
import com.myProjects.soru_cozum.service.StudentService;

/**
 * Rest controller for sign up requests
 * @author mehmetozanguven
 *
 */
@RestController()
@RequestMapping("/signup")
public class SingupController {
	@Autowired
	private StudentService studentService;
	
	/**
	 * Register new student
	 * Check student exists in the database via name and password
	 * If exists return message that states "student exists"
	 * Else create new student and return that student
	 * @param newRegisterRequest is request body
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> registerNewStudent(@RequestBody NewRegisterRequest newRegisterRequest){
		boolean isStudentExists = studentService.checkStudentExistsWithUsernameAndPassword(newRegisterRequest.getStudentName(), newRegisterRequest.getStudentPassword());
		
		if (isStudentExists)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SignupResponse("Error", "Already have"));
		
		Student newStudent = studentService.createStudentFromRequest(newRegisterRequest);
		studentService.registerNewStudent(newStudent);
		
		return ResponseEntity.ok(newStudent);
	}
	
	
	
}
