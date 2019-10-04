package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.request.service.StudentQuestionUploadRequest;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;


/**
 * Student ask question handler, this handler will upload the new question to the server.
 * @author mehmetozanguven
 *
 */
public class FileStorageHandler extends StudentAskQuestionAbstractHandler{

	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequest request) {
		MultipartFile file = request.getQuestionMultipartFile();
		Long publisherId = request.getPublisher().get().getId();
		String questionCategory = request.getQuestionCategory();
		String questionSubCategory = request.getQuestionSubCategory();
		int pageNumber = request.getPageNumber();
		int questionNumber = request.getQuestionNumber();
		
		StudentQuestionUploadRequest serviceRequest = new StudentQuestionUploadRequest();
		serviceRequest.setPageNumber(pageNumber);
		serviceRequest.setPublisherId(publisherId);
		serviceRequest.setQuestionCategory(questionCategory);
		serviceRequest.setQuestionImageFile(file);
		serviceRequest.setQuestionNumber(questionNumber);
		serviceRequest.setQuestionSubCategory(questionSubCategory);
		
		Optional<StudentQuestionUploadResponse> uploadRes = request.getFileStorageService().storeStudentQuestion_new(serviceRequest);		
		request.setUploadResponse(uploadRes.get());
		
		return getNextHandler().handle(request);
	}
	
}
