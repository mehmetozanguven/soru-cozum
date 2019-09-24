package com.myProjects.soru_cozum.chainPattern.teacher.answer;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.request.service.TeacherAnswerImageUploadServiceRequest;
import com.myProjects.soru_cozum.response.TeacherResponse;
import com.myProjects.soru_cozum.response.service.TeacherAnswerImageServiceResponse;

public class AnswerQuestionHandler extends TeacherAnswerAbstractHandler{
	
	
	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		TeacherAnswerImageUploadServiceRequest serviceRequest = prepareAnswerImageServiceRequest(request);
		
		Optional<TeacherAnswerImageServiceResponse> serviceResponse = request.getFileStorageService().storeTeacherAnswerImage(serviceRequest);
		
		if (!serviceResponse.isPresent()) {
			getResponse().setStatu("Error");
			getResponse().setInformation(new TeacherResponse("Can't upload your answer"));
			return new ResponseEntity<>(getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		request.getQuestion().get().addTeacherToQuestion(request.getTeacher().get());
		request.getQuestion().get().setAnswered(true);
		request.getQuestionService().updateQuestion(request.getQuestion().get());
		getResponse().setStatu("Success");
		TeacherResponse controllerRes = new TeacherResponse("Your answer was sent");
		controllerRes.setImageJson(serviceResponse.get());
		getResponse().setInformation(controllerRes);
		return new ResponseEntity<>(getResponse(), HttpStatus.OK);
	}
	
	private TeacherAnswerImageUploadServiceRequest prepareAnswerImageServiceRequest(TeacherAnswerRequest handlerRequest) {
		TeacherAnswerImageUploadServiceRequest serviceRequest = new TeacherAnswerImageUploadServiceRequest();
		Question question = handlerRequest.getQuestion().get();
		
		serviceRequest.setAnswerImageFile(handlerRequest.getImageFile());
		serviceRequest.setPageNumber(question.getPageNumber());
		serviceRequest.setPublisherId(question.getPublisher().getId());
		serviceRequest.setQuestionCategory(question.getQuestionCategory());
		serviceRequest.setQuestionId(question.getId());
		serviceRequest.setQuestionNumber(question.getQuestionNumber());
		serviceRequest.setQuestionSubCategory(question.getQuestionSubCategory());
		serviceRequest.setTeacherId(handlerRequest.getTeacher().get().getId());
		
		return serviceRequest;
		
	}
}
