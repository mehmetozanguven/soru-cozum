package com.myProjects.soru_cozum.chainPattern.teacher.answer;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.AnswerAudio;
import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.request.service.TeacherAnswerAudioUploadServiceRequest;
import com.myProjects.soru_cozum.request.service.TeacherAnswerImageUploadServiceRequest;
import com.myProjects.soru_cozum.response.TeacherResponse;
import com.myProjects.soru_cozum.response.service.TeacherAnswerAudioServiceResponse;
import com.myProjects.soru_cozum.response.service.TeacherAnswerImageServiceResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnswerQuestionHandler extends TeacherAnswerAbstractHandler{
	private final Logger LOGGER = LoggerFactory.getLogger(AnswerQuestionHandler.class);
	
	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		LOGGER.trace("Answer the question image file: " + request.getImageFile() + "\n audio file: " + request.getAnswerAudioFile());
		if (request.getImageFile() != null)
			return answerImage(request);
		else if (request.getAnswerAudioFile() != null)
			return answerAudio(request);
		else {
			LOGGER.error("Both image & audio file are null, then return error Can't upload your answer");
			getResponse().setStatu("Error");
			getResponse().setInformation(new TeacherResponse("Can't upload your answer"));
			return new ResponseEntity<>(getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	private ResponseEntity<?> answerImage(TeacherAnswerRequest handlerRequest) {
		LOGGER.trace("Teacher will answer with an image");
		TeacherAnswerImageUploadServiceRequest serviceRequest = prepareAnswerImageServiceRequest(handlerRequest);
		LOGGER.trace("AnswerImageRequest: " + serviceRequest);
		Question question = handlerRequest.getQuestion().get();

		Long questionId = question.getId();
		Long publisherId = question.getPublisher().getId();
		LOGGER.trace("AnswerImageRequest is sending to the file storage service");
		Optional<TeacherAnswerImageServiceResponse> serviceResponse = handlerRequest.getFileStorageService().storeTeacherAnswerImage(serviceRequest);
		LOGGER.trace("Response from file storage service: " + serviceResponse);
		if (!serviceResponse.isPresent()) {
			getResponse().setStatu("Error");
			getResponse().setInformation(new TeacherResponse("Can't upload your answer"));
			return new ResponseEntity<>(getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		AnswerImage answerImage = new AnswerImage(publisherId, questionId);
		handlerRequest.getTeacher().get().addImageToTeacher(answerImage);
		question.addTeacherToQuestion(handlerRequest.getTeacher().get());
		question.setImageAnswered(true);
		handlerRequest.getQuestionService().updateQuestion(question);
		getResponse().setStatu("Success");
		TeacherResponse controllerRes = new TeacherResponse("Your answer was sent");
		controllerRes.setImageJson(serviceResponse.get());
		getResponse().setInformation(controllerRes);
		return new ResponseEntity<>(getResponse(), HttpStatus.OK);
	}
	
	private ResponseEntity<?> answerAudio(TeacherAnswerRequest handlerRequest) {
		LOGGER.trace("Teacher will answer with an audio");
		TeacherAnswerAudioUploadServiceRequest serviceRequest = prepareAnswerAudioServiceRequest(handlerRequest);
		LOGGER.trace("AnswerAudioRequest: " + serviceRequest);
		Question question = handlerRequest.getQuestion().get();

		Long questionId = question.getId();
		Long publisherId = question.getPublisher().getId();
		LOGGER.trace("AnswerAudioRequest is sending to the file storage service");
		Optional<TeacherAnswerAudioServiceResponse> serviceResponse = handlerRequest.getFileStorageService().storeTeacherAnswerAudio(serviceRequest);
		LOGGER.trace("Response from file storage service: " + serviceResponse);
		if (!serviceResponse.isPresent()) {
			getResponse().setStatu("Error");
			getResponse().setInformation(new TeacherResponse("Can't upload your answer"));
			return new ResponseEntity<>(getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		AnswerAudio answerAudio = new AnswerAudio(publisherId, questionId);
		handlerRequest.getTeacher().get().addAudioToTeacher(answerAudio);
		question.addTeacherToQuestion(handlerRequest.getTeacher().get());
		question.setAudioAnswered(true);
		handlerRequest.getQuestionService().updateQuestion(question);
		getResponse().setStatu("Success");
		TeacherResponse controllerRes = new TeacherResponse("Your answer was sent");
		controllerRes.setAudioJson(serviceResponse.get());
		getResponse().setInformation(controllerRes);
		return new ResponseEntity<>(getResponse(), HttpStatus.OK);
	}

	
	
	private TeacherAnswerAudioUploadServiceRequest prepareAnswerAudioServiceRequest(TeacherAnswerRequest handlerRequest) {
		TeacherAnswerAudioUploadServiceRequest serviceRequest = new TeacherAnswerAudioUploadServiceRequest();
		Question question = handlerRequest.getQuestion().get();
		
		serviceRequest.setAnswerAudioFile(handlerRequest.getImageFile());
		serviceRequest.setPageNumber(question.getPageNumber());
		serviceRequest.setPublisherId(question.getPublisher().getId());
		serviceRequest.setQuestionCategory(question.getQuestionCategory());
		serviceRequest.setQuestionId(question.getId());
		serviceRequest.setQuestionNumber(question.getQuestionNumber());
		serviceRequest.setQuestionSubCategory(question.getQuestionSubCategory());
		serviceRequest.setTeacherId(handlerRequest.getTeacher().get().getId());
		
		return serviceRequest;
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
