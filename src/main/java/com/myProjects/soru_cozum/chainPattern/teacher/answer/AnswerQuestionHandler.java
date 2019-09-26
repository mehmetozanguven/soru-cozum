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

public class AnswerQuestionHandler extends TeacherAnswerAbstractHandler{
	
	
	@Override
	public ResponseEntity<?> handle(TeacherAnswerRequest request) {
		
		if (request.getImageFile() != null)
			return answerImage(request);
		else if (request.getAnswerAudioFile() != null)
			return answerAudio(request);
		else {
			getResponse().setStatu("Error");
			getResponse().setInformation(new TeacherResponse("Can't upload your answer"));
			return new ResponseEntity<>(getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	private ResponseEntity<?> answerImage(TeacherAnswerRequest handlerRequest) {
		TeacherAnswerImageUploadServiceRequest serviceRequest = prepareAnswerImageServiceRequest(handlerRequest);
		Question question = handlerRequest.getQuestion().get();

		Long questionId = question.getId();
		Long publisherId = question.getPublisher().getId();
		
		Optional<TeacherAnswerImageServiceResponse> serviceResponse = handlerRequest.getFileStorageService().storeTeacherAnswerImage(serviceRequest);
		
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
		TeacherAnswerAudioUploadServiceRequest serviceRequest = prepareAnswerAudioServiceRequest(handlerRequest);
		Question question = handlerRequest.getQuestion().get();

		Long questionId = question.getId();
		Long publisherId = question.getPublisher().getId();

		Optional<TeacherAnswerAudioServiceResponse> serviceResponse = handlerRequest.getFileStorageService().storeTeacherAnswerAudio(serviceRequest);
		
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
