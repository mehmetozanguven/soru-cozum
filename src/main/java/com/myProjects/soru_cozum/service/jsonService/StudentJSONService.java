package com.myProjects.soru_cozum.service.jsonService;

import java.util.List;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.json.StudentQuestionJSON;

public interface StudentJSONService {
	
	List<StudentQuestionJSON> getStudentAnswerList(Student student);
	
	List<StudentQuestionJSON> getStudentQuestions_new(Student student);

}
