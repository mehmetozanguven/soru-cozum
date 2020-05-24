package com.myProjects.soru_cozum.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.myProjects.soru_cozum.chainPattern.signup.student.CreateNewStudentDetailsHandler;
import com.myProjects.soru_cozum.chainPattern.signup.student.CreateNewStudentHandler;
import com.myProjects.soru_cozum.chainPattern.signup.student.FindStudentByUsernameHandler;
import com.myProjects.soru_cozum.chainPattern.signup.student.StudentSignupAbstractHandler;
import com.myProjects.soru_cozum.chainPattern.signup.student.StudentSignupRequest;
import com.myProjects.soru_cozum.chainPattern.signup.teacher.CreateNewTeacherDetailsHandler;
import com.myProjects.soru_cozum.chainPattern.signup.teacher.CreateNewTeacherHandler;
import com.myProjects.soru_cozum.chainPattern.signup.teacher.FindTeacherByUsernameHandler;
import com.myProjects.soru_cozum.chainPattern.signup.teacher.TeacherSignupAbstractHandler;
import com.myProjects.soru_cozum.chainPattern.signup.teacher.TeacherSignupRequest;
import com.myProjects.soru_cozum.request.GenericSignupRequest;
import com.myProjects.soru_cozum.service.StudentService;
import com.myProjects.soru_cozum.service.TeacherService;

/**
 * Rest controller for sign up requests
 * @author mehmetozanguven
 *
 */
@RestController()
@RequestMapping("/signup")
@CrossOrigin(origins = "http://localhost", maxAge = 3600)
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
	private TeacherSignupAbstractHandler createNewTeacherDetails;
	
	private StudentSignupAbstractHandler findStudentByUsernameHandler;
	private StudentSignupAbstractHandler createNewStudentHandler;
	private StudentSignupAbstractHandler createNewStudentDetailsHandler;
	
	public SingupController() {
		findTeacherByUsernameHandler = new FindTeacherByUsernameHandler();
		createNewTeacherHandler = new CreateNewTeacherHandler();
		createNewTeacherDetails = new CreateNewTeacherDetailsHandler();
		
		findStudentByUsernameHandler = new FindStudentByUsernameHandler();
		createNewStudentHandler = new CreateNewStudentHandler();
		createNewStudentDetailsHandler = new CreateNewStudentDetailsHandler();
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
	public ResponseEntity<?> registerNewStudent(@RequestBody GenericSignupRequest newRegisterRequest){
		LOGGER.debug("Register new student");
		StudentSignupRequest request = new StudentSignupRequest(passwordEncoder, newRegisterRequest, studentService);
		findStudentByUsernameHandler.setNextHandler(createNewStudentDetailsHandler);
		createNewStudentDetailsHandler.setNextHandler(createNewStudentHandler);
		
		return findStudentByUsernameHandler.handle(request);
	}
	
	
	@PostMapping("/teacher")
	public ResponseEntity<?> registerNewTeacher(@RequestBody GenericSignupRequest newRegisterRequest){
		LOGGER.debug("Register new teacher");
		TeacherSignupRequest request = new TeacherSignupRequest(passwordEncoder, newRegisterRequest, teacherService);
		findTeacherByUsernameHandler.setNextHandler(createNewTeacherDetails);
		createNewTeacherDetails.setNextHandler(createNewTeacherHandler);
		
		return findTeacherByUsernameHandler.handle(request);
	}
	
}
