package com.myProjects.soru_cozum.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.enums.StoreType;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;

public interface FileStorageService {
	StudentQuestionUploadResponse storeFile(MultipartFile file, StoreType types, Integer pageNumber,
			Integer questionNumber, Long publisherId, QuestionCategory questionCategory, String questionSubCategory);

	StudentQuestionUploadResponse storeFile(MultipartFile file, StoreType types, Integer pageNumber,
			Integer questionNumber, Long publisherId, String questionCategory, String questionSubCategory);

	Resource loadFileAsResource(String fileName, StoreType types);
}
