package com.myProjects.soru_cozum.service.jsonService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.json.PublisherJSON;
import com.myProjects.soru_cozum.model.json.QuestionJSON;
import com.myProjects.soru_cozum.response.StudentQuestionAnswerResponse;

@Service
public class StudentJSONServiceImpl implements StudentJSONService{
	@Override
	public List<StudentQuestionAnswerResponse> getStudentQuestions(Student student) {
		List<StudentQuestionAnswerResponse> questionList = new ArrayList<StudentQuestionAnswerResponse>();
		
		for (Question eachQuestion : student.getStudentQuestions()) {
			QuestionJSON questionJSON = new QuestionJSON(eachQuestion.getId(), eachQuestion.getQuestionImage().getId(),
										eachQuestion.isAnswered());
			
			PublisherJSON publisherJSON = new PublisherJSON(eachQuestion.getPublisher().getId(), eachQuestion.getPublisher().getName(), eachQuestion.getPublisher().getPublishYear());
			StudentQuestionAnswerResponse studentQuestionResponse = new StudentQuestionAnswerResponse(questionJSON, publisherJSON);
			questionList.add(studentQuestionResponse);
		}
		
		return questionList;
	}
	
	@Override
	public List<StudentQuestionAnswerResponse> getStudentAnswerList(Student student) {
		List<StudentQuestionAnswerResponse> answerList = new ArrayList<StudentQuestionAnswerResponse>();
		List<StudentQuestionAnswerResponse> questionList = getStudentQuestions(student);
		
		questionList.stream().filter(elem -> elem.getQuestionJSON().isAnswered()).forEach(elem -> answerList.add(elem));
		// TODO Auto-generated method stub
		return answerList;
	}
	
	
}
