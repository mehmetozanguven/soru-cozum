package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.springframework.http.ResponseEntity;



public class FileStorageHandler extends StudentAskQuestionAbstractHandler{

	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequest request) {
//		String questionFilePath = request.getFileStorageService().storeFile(
//				request.getQuestionMultipartFile(), StoreType.STUDENT_QUESTION,
//				request.getAddQuestionToStudentRequest().getStudentId());
//		
//		if (questionFilePath.startsWith("Sorry!")) {
//			return new ResponseEntity<>(new AddQuestionToStudentErrorResponse("Error", "Can't store the file"), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		request.setQuestionFilePath(questionFilePath);
		return getNextHandler().handle(request);
	}
	
}
