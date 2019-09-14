package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.request.AddQuestionRequest;
import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.response.StudentAskQuestionResponse;
import com.myProjects.soru_cozum.service.QuestionService;

public abstract class StudentAskQuestionAbstractHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentAskQuestionAbstractHandler.class);
	
	private StudentAskQuestionAbstractHandler nextHandler;
	
	private GenericResponse<StudentAskQuestionResponse> response;
	
	
	
	public StudentAskQuestionAbstractHandler() {
		response = new GenericResponse<StudentAskQuestionResponse>();
	}

	public abstract ResponseEntity<?> handle(StudentAskQuestionRequest request);
	
	public void setNextHandler(StudentAskQuestionAbstractHandler handler) {
		this.nextHandler = handler;
	}
	
	public StudentAskQuestionAbstractHandler getNextHandler() {
		return this.nextHandler;
	}
	
	
	
	public GenericResponse<StudentAskQuestionResponse> getResponse() {
		return response;
	}

	public Question createNewQuestion(String filePath, AddQuestionRequest addQuestionToStudentRequest, boolean isNewPublisher,
			Publisher newPublisher, QuestionService questionService) {
		int pageNumber = addQuestionToStudentRequest.getPageNumber();
		int questionNumber = addQuestionToStudentRequest.getQuestionNumber();
		QuestionCategory questionCategory = addQuestionToStudentRequest.getQuestionCategory();
		String questionSubCategory = addQuestionToStudentRequest.getQuestionSubCategory();
		
		String fileDownloadUri = createFileDownloadUri(filePath);
		LOGGER.debug("File Downloand uri: " + fileDownloadUri);

		Question newQuestion = questionService.createNewQuestionWithCommonProperties_multipart(pageNumber, questionNumber,
				questionCategory, questionSubCategory, fileDownloadUri);
		if (isNewPublisher)
			questionService.addPublisherToQuestionn(newQuestion, newPublisher);
		return newQuestion;

	}
	
	public String createFileDownloadUri(String fileName) {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
		return fileDownloadUri;
	}

/*
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
	*/
}
