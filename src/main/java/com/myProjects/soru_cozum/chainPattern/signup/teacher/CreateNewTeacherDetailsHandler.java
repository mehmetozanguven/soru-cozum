package com.myProjects.soru_cozum.chainPattern.signup.teacher;

import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.enums.Department;
import com.myProjects.soru_cozum.model.TeacherDetails;

public class CreateNewTeacherDetailsHandler extends TeacherSignupAbstractHandler{

	
	@Override
	public ResponseEntity<?> handle(TeacherSignupRequest request) {
		String schoolName = request.getNewRegisterRequest().getSchoolName();
		Department department = request.getNewRegisterRequest().getDepartment();
		TeacherDetails teacherDetails = request.getTeacherService().createTeacherDetails(schoolName, department);
		request.setTeacherDetails(teacherDetails);
		
		return getNextHandler().handle(request);
	}
	
}
