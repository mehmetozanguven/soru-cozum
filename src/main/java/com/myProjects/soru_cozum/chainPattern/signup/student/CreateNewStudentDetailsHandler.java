package com.myProjects.soru_cozum.chainPattern.signup.student;

import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.enums.Department;
import com.myProjects.soru_cozum.model.StudentDetails;

public class CreateNewStudentDetailsHandler extends StudentSignupAbstractHandler{

	@Override
	public ResponseEntity<?> handle(StudentSignupRequest request) {
		String schoolName = request.getNewRegisterRequest().getSchoolName();
		Department department = request.getNewRegisterRequest().getDepartment();
		String classNum = "";
		
		if (request.getNewRegisterRequest().getClassNum() != null)
			classNum = request.getNewRegisterRequest().getClassNum();
		
		StudentDetails studentDetails = request.getStudentService().createStudentDetails(schoolName, classNum, department);
		request.setStudentDetails(studentDetails);
		return getNextHandler().handle(request);
	}
	
}
