package com.myProjects.soru_cozum.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProjects.soru_cozum.chainPattern.signup.student.CreateNewStudentHandler;
import com.myProjects.soru_cozum.chainPattern.signup.student.FindStudentByUsernameHandler;
import com.myProjects.soru_cozum.chainPattern.signup.student.StudentSignupAbstractHandler;
import com.myProjects.soru_cozum.chainPattern.signup.student.StudentSignupRequest;
import com.myProjects.soru_cozum.chainPattern.signup.teacher.CreateNewTeacherHandler;
import com.myProjects.soru_cozum.chainPattern.signup.teacher.FindTeacherByUsernameHandler;
import com.myProjects.soru_cozum.chainPattern.signup.teacher.TeacherSignupAbstractHandler;
import com.myProjects.soru_cozum.chainPattern.signup.teacher.TeacherSignupRequest;
import com.myProjects.soru_cozum.request.NewRegisterRequestForStudent;
import com.myProjects.soru_cozum.request.NewRegisterRequestForTeacher;
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
	private final Logger LOGGER = LoggerFactory.getLogger(SingupController.class);
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private TeacherSignupAbstractHandler findTeacherByUsernameHandler;
	private TeacherSignupAbstractHandler createNewTeacherHandler;
	
	private StudentSignupAbstractHandler findStudentByUsernameHandler;
	private StudentSignupAbstractHandler createNewStudentHandler;
	
	public SingupController() {
		findTeacherByUsernameHandler = new FindTeacherByUsernameHandler();
		createNewTeacherHandler = new CreateNewTeacherHandler();
		
		findStudentByUsernameHandler = new FindStudentByUsernameHandler();
		createNewStudentHandler = new CreateNewStudentHandler();
	}
	
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
		StudentSignupRequest request = new StudentSignupRequest(passwordEncoder, newRegisterRequest, studentService);
		findStudentByUsernameHandler.setNextHandler(createNewStudentHandler);
		
		return findStudentByUsernameHandler.handle(request);
	}
	
	
	@PostMapping("/teacher")
	public ResponseEntity<?> registerNewTeacher(@RequestBody NewRegisterRequestForTeacher newRegisterRequest){
		TeacherSignupRequest request = new TeacherSignupRequest(passwordEncoder, newRegisterRequest, teacherService);
		findTeacherByUsernameHandler.setNextHandler(createNewTeacherHandler);
		
		return findTeacherByUsernameHandler.handle(request);
	}
	
}
