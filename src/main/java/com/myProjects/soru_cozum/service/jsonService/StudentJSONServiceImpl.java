package com.myProjects.soru_cozum.service.jsonService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.json.StudentQuestionJSON;
import com.myProjects.soru_cozum.model.json.student.AnswerAudioJSON;
import com.myProjects.soru_cozum.model.json.student.AnswerImageJSON;
import com.myProjects.soru_cozum.model.json.student.QuestionImageJSON;

/**
 * This service is responsible to find student's question(s)
 * @author mehmetozanguven
 *
 */
@Service
public class StudentJSONServiceImpl implements StudentJSONService{
	
	
	/**
	 * Returns all student's question(s)
	 */
	@Override
	public List<StudentQuestionJSON> getStudentQuestions_new(Student student) {
		List<StudentQuestionJSON> questionListt = new ArrayList<StudentQuestionJSON>();
		
		student.getStudentQuestions().forEach(eachQuestion -> {
			QuestionImageJSON questionImageJSON = QuestionImageJSON.createQuestionJSON(eachQuestion, eachQuestion.getPublisher());
			List<AnswerImageJSON> answerImageJSONList = new ArrayList<AnswerImageJSON>();
			List<AnswerAudioJSON> answerAudioJSONList = new ArrayList<AnswerAudioJSON>();

			if (eachQuestion.isImageAnswered())
				answerImageJSONList = AnswerImageJSON.createAnswerImageJSONList(eachQuestion, eachQuestion.getPublisher());
			if (eachQuestion.isAudioAnswered())
				answerAudioJSONList = AnswerAudioJSON.createAnswerImageJSONList(eachQuestion, eachQuestion.getPublisher());
			StudentQuestionJSON newStudentQuestionJSON = new StudentQuestionJSON(questionImageJSON, answerImageJSONList, answerAudioJSONList);
			questionListt.add(newStudentQuestionJSON);

		});
		
		return questionListt;
	}
	
	/**
	 * Returns all answered question(s)
	 */
	@Override
	public List<StudentQuestionJSON> getStudentAnswerList(Student student) {
		List<StudentQuestionJSON> questionListt = new ArrayList<StudentQuestionJSON>();
		
		student.getStudentQuestions().forEach(eachQuestion -> {
			QuestionImageJSON questionImageJSON = QuestionImageJSON.createQuestionJSON(eachQuestion,
					eachQuestion.getPublisher());
			List<AnswerImageJSON> answerImageJSONList = new ArrayList<AnswerImageJSON>();
			List<AnswerAudioJSON> answerAudioJSONList = new ArrayList<AnswerAudioJSON>();
			if (eachQuestion.isImageAnswered())
				answerImageJSONList = AnswerImageJSON.createAnswerImageJSONList(eachQuestion,
						eachQuestion.getPublisher());
			if (eachQuestion.isAudioAnswered())
				answerAudioJSONList = AnswerAudioJSON.createAnswerImageJSONList(eachQuestion,
						eachQuestion.getPublisher());
			StudentQuestionJSON newStudentQuestionJSON = new StudentQuestionJSON(questionImageJSON, answerImageJSONList,
					answerAudioJSONList);
			questionListt.add(newStudentQuestionJSON);
		});
		
		return questionListt;
	}
	
//	@Override
//	public List<StudentQuestionListResponse> getStudentAnswerList(Student student) {
//		List<StudentQuestionListResponse> answerList = new ArrayList<StudentQuestionListResponse>();
//		List<StudentQuestionListResponse> questionList = getStudentQuestions(student);
//		
//		questionList.stream().filter(elem -> elem.getQuestionJSON().isAnswered()).forEach(elem -> answerList.add(elem));
//		return answerList;
//	}
	
	
}
