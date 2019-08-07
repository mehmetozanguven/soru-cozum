package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.request.AddQuestionToStudentRequest;
import com.myProjects.soru_cozum.service.QuestionService;

public abstract class StudentAskQuestionHandler {
	private StudentAskQuestionHandler nextHandler;
	
	
	public abstract ResponseEntity<?> handle(StudentAskQuestionRequestHandler request);
	
	public void setNextHandler(StudentAskQuestionHandler handler) {
		this.nextHandler = handler;
	}
	
	public StudentAskQuestionHandler getNextHandler() {
		return this.nextHandler;
	}
	
	public Question createNewQuestion(AddQuestionToStudentRequest addQuestionToStudentRequest, boolean isNewPublisher,
			Publisher newPublisher, QuestionService questionService) {
		int pageNumber = addQuestionToStudentRequest.getPageNumber();
		int questionNumber = addQuestionToStudentRequest.getQuestionNumber();
		QuestionCategory questionCategory = addQuestionToStudentRequest.getQuestionCategory();
		String questionSubCategory = addQuestionToStudentRequest.getQuestionSubCategory();
		tempConvertStringToImageByte(addQuestionToStudentRequest);
		byte[] questionImageByte = addQuestionToStudentRequest.getImageByte();

		Question newQuestion = questionService.createNewQuestionWithCommonProperties(pageNumber, questionNumber,
				questionCategory, questionSubCategory, questionImageByte);
		if (isNewPublisher)
			questionService.addPublisherToQuestionn(newQuestion, newPublisher);
		return newQuestion;

	}

	private void tempConvertStringToImageByte(AddQuestionToStudentRequest addQuestionToStudentRequest) {
		File file = new File(addQuestionToStudentRequest.getFilePath());
		byte[] bFile = new byte[(int) file.length()];

		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			// convert file into array of bytes
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		addQuestionToStudentRequest.setImageByte(bFile);

	}
	
}
