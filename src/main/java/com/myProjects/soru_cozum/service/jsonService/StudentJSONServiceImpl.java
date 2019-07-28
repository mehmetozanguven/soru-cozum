package com.myProjects.soru_cozum.service.jsonService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.json.QuestionJSON;
import com.myProjects.soru_cozum.response.StudentQuestionResponse;

@Service
public class StudentJSONServiceImpl implements StudentJSONService{
	@Override
	public List<StudentQuestionResponse> getStudentQuestions(Student student) {
		List<StudentQuestionResponse> questionList = new ArrayList<StudentQuestionResponse>();
		
		for (Question eachQuestion : student.getStudentQuestions()) {
			QuestionJSON questionJSON = new QuestionJSON(eachQuestion.getId(), eachQuestion.getQuestionImage().getId());
		}
		
		return null;
	}
}
