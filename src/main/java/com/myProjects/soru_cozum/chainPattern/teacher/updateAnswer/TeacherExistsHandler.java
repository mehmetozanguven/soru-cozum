package com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.response.TeacherResponse;

public class TeacherExistsHandler extends TeacherUpdateAnswerAbstactHandler{

	@Override
	public ResponseEntity<?> handle(TeacherUpdateAnswerRequest request) {
		Optional<Teacher> teacher = request.getTeacherService().findTeacherById(request.getUserRequest().getTeacherId());
		
		if (teacher.isPresent()) {
			request.setTeacher(teacher.get());
			return getNextHandler().handle(request);
		}
		
		getResponse().setStatu("Error");
		getResponse().setInformation(new TeacherResponse("Invalid teacher id"));
		return new ResponseEntity<>(getResponse(), HttpStatus.NOT_FOUND);
	}
	
	
}
