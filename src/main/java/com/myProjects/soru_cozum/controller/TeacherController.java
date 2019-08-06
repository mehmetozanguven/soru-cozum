package com.myProjects.soru_cozum.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.model.json.AnsweredQuestionJSON;
import com.myProjects.soru_cozum.request.AnswerQuestionRequest;
import com.myProjects.soru_cozum.response.AnswerQuestionResponse;
import com.myProjects.soru_cozum.service.QuestionService;
import com.myProjects.soru_cozum.service.TeacherService;

/**
 * @author mehmetozanguven
 *
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping("/allQuestion/{categoryName}")
	public ResponseEntity<?> getAllNonAnsweredQuestionBySpecificType(@PathVariable("categoryName") QuestionCategory questionCategory){
		List<Question> allSpecificQuestions = questionService.getAllNonAnsweredQuestionsBySpecificType(questionCategory);
		return ResponseEntity.ok(allSpecificQuestions);
	}
	
	@GetMapping("/getQuestionImage/{questionId}")
	public ResponseEntity<?> getQuestionImageByQuestionId(@PathVariable("questionId") Long questionId){
		QuestionImage questionImage = questionService.getQuestionImageByQuestionId(questionId);
		if (questionImage.getId() == 0)
			return new ResponseEntity<>(new AnswerQuestionResponse("ERROR", "Invalid question image id"), HttpStatus.NOT_FOUND);
		else
			return ResponseEntity.ok().body(questionImage);
	}
	
	@GetMapping("/getMyAnsweredQuestion/{teacherId}")
	public ResponseEntity<?> getAnsweredQuestionByTeacherId(@PathVariable(value = "teacherId") Long teacherId){
		Teacher teacher = teacherService.getTeacherById(teacherId);
		if (teacher.getId() == 0)
			return new ResponseEntity<>(new AnswerQuestionResponse("ERROR", "Invalid teacher id"), HttpStatus.NOT_FOUND);
		List<AnsweredQuestionJSON> answerList = teacherService.getAnsweredQuestionByTeacherId(teacher);
		return new ResponseEntity<>(answerList, HttpStatus.OK);
	}
	
	/**
	 * Answer the specific question
	 * <ul>
	 * 	<li> Checks the teacher id is valid, if not return error </li>
	 * 	<li> Checks the question id is valid, if not return error </li>
	 * 	<li> Update teacher answer image and video properties </li>
	 * 	<li> Update Question and save to the database </li>
	 * </ul>
	 * @param answerQuestionRequest
	 * @return
	 */
	@PostMapping("/answerQuestion")
	public ResponseEntity<?> answerQuestion(@RequestBody AnswerQuestionRequest answerQuestionRequest){
		Teacher teacher = teacherService.getTeacherById(answerQuestionRequest.getTeacherId());
		
		if (teacher.getId() == 0)
			return new ResponseEntity<>(new AnswerQuestionResponse("ERROR", "Invalid teacher id"), HttpStatus.NOT_FOUND);
		
		Question question = questionService.findQuestionById(answerQuestionRequest.getQuestionId());
		if (question.getId() == 0)
			return new ResponseEntity<>(new AnswerQuestionResponse("ERROR", "Invalid question id"), HttpStatus.NOT_FOUND);
		
		
		teacherService.resolveTeacherAccordingToAnswerQuestion(teacher, question, answerQuestionRequest);
		question.addTeacherToQuestion(teacher);
		question.setAnswered(true);
		questionService.updateQuestion(question);
		
		return new ResponseEntity<>(new AnswerQuestionResponse("SUCCESS", "Your answer was sent"), HttpStatus.OK);
	}
}
