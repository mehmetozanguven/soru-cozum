package com.myProjects.soru_cozum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.service.QuestionService;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/allQuestion/{categoryName}")
	public ResponseEntity<?> getAllQuestionBySpecificType(@PathVariable("categoryName") QuestionCategory questionCategory){
		List<Question> allSpecificQuestions = questionService.getAllQuestionsBySpecificType(questionCategory);
		return ResponseEntity.ok(allSpecificQuestions);
	}
}
