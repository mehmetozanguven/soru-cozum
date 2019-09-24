package com.myProjects.soru_cozum.service;

import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.enums.StoreType;
import com.myProjects.soru_cozum.request.service.TeacherAnswerAudioUploadServiceResponse;
import com.myProjects.soru_cozum.request.service.TeacherAnswerImageUploadServiceRequest;
import com.myProjects.soru_cozum.response.AnswerImageResponse;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;
import com.myProjects.soru_cozum.response.service.TeacherAnswerAudioServiceRequest;
import com.myProjects.soru_cozum.response.service.TeacherAnswerImageServiceResponse;

public interface FileStorageService {

	Optional<TeacherAnswerAudioUploadServiceResponse> storeTeacherAnswerAudio(TeacherAnswerAudioServiceRequest serviceRequest);
	
	Optional<TeacherAnswerImageServiceResponse> storeTeacherAnswerImage(TeacherAnswerImageUploadServiceRequest serviceRequest);
	
	StudentQuestionUploadResponse storeStudentQuestion(MultipartFile file, StoreType types, Integer pageNumber,
			Integer questionNumber, Long publisherId, String questionCategory, String questionSubCategory);
	
	
	Resource loadFileAsResource(String fileName, StoreType types);
	
	Optional<Resource> loadFileAsResource_opt(String filePath);

	String createAnswerImageFilePath(TeacherAnswerImageServiceResponse userRequest);

	String createAnswerAudioFilePath(TeacherAnswerAudioUploadServiceResponse userRequest);
	
	
}
