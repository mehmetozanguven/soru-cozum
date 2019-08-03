package com.myProjects.soru_cozum.service.jsonService;

import java.util.List;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.response.StudentQuestionAnswerResponse;

public interface StudentJSONService {

	List<StudentQuestionAnswerResponse> getStudentQuestions(Student student);
	
	List<StudentQuestionAnswerResponse> getStudentAnswerList(Student student);

}
