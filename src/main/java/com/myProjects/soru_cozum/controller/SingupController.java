package com.myProjects.soru_cozum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.request.NewRegisterRequestForStudent;
import com.myProjects.soru_cozum.request.NewRegisterRequestForTeacher;
import com.myProjects.soru_cozum.response.SignupResponse;
import com.myProjects.soru_cozum.service.StudentService;
import com.myProjects.soru_cozum.service.TeacherService;

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
	
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * Register new student
	 * Check student exists in the database via name and password
	 * If exists return message that states "student exists"
	 * Else create new student and return that student
	 * @param newRegisterRequest is request body
	 * @return
	 */
	@PostMapping("/student")
	public ResponseEntity<?> registerNewStudent(@RequestBody NewRegisterRequestForStudent newRegisterRequest){
		boolean isStudentExists = studentService.checkStudentExistsWithUsernameAndPassword(newRegisterRequest.getStudentName(), newRegisterRequest.getStudentPassword());
		
		if (isStudentExists)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SignupResponse("Error", "Already have"));
		
		Student newStudent = studentService.createStudentFromRequest(newRegisterRequest);
		studentService.registerNewStudent(newStudent);
		
		return ResponseEntity.ok(newStudent);
	}
	
	
	@PostMapping("/teacher")
	public ResponseEntity<?> registerNewTeacher(@RequestBody NewRegisterRequestForTeacher newRegisterRequest){
		boolean isTeacherExists = teacherService.checksTeacherExistsWithUsernameAndPassword(newRegisterRequest.getName(), newRegisterRequest.getPassword());
		
		if (isTeacherExists)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SignupResponse("Error", "Already have"));
		
		Teacher teacher = teacherService.createTeacherFromRequest(newRegisterRequest.getName(), newRegisterRequest.getPassword());
		teacherService.registerNewTeacher(teacher);
		
		return ResponseEntity.status(HttpStatus.OK).body(teacher);
		
	}
	
	
	
	
}
