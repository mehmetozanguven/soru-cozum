package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.enums.StoreType;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;



public class FileStorageHandler extends StudentAskQuestionAbstractHandler{

	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequest request) {
		MultipartFile file = request.getQuestionMultipartFile();
		StoreType type = StoreType.STUDENT_QUESTION;
		Long publisherId = request.getPublisher().get().getId();
		String questionCategory = request.getQuestionCategory();
		String questionSubCategory = request.getQuestionSubCategory();
		int pageNumber = request.getPageNumber();
		int questionNumber = request.getQuestionNumber();
		
		StudentQuestionUploadResponse uploadRes = request.getFileStorageService()
														.storeFile(file, type, pageNumber, questionNumber, publisherId, questionCategory, questionSubCategory);
		
		request.setUploadResponse(uploadRes);
		
		return getNextHandler().handle(request);
	}
	
}
