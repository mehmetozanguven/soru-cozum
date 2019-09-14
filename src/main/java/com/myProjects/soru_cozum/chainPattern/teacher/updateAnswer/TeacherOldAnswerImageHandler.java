package com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.response.TeacherResponse;

public class TeacherOldAnswerImageHandler extends TeacherUpdateAnswerAbstactHandler{
	private final Logger LOGGER = LoggerFactory.getLogger(TeacherOldAnswerImageHandler.class);
	
	@Override
	public ResponseEntity<?> handle(TeacherUpdateAnswerRequest request) {
		if (request.getUserRequest().getImageByte() != null) {
			Optional<AnswerImage> answerImage = request.getTeacherService().findAnswerImageFromTeacher(request.getUserRequest().getTeacherId(), request.getUserRequest().getQuestionId());
			
			if (answerImage.isPresent()) {
				request.setOldAnswerImage(answerImage.get());
				getNextHandler().handle(request);
			}
			getResponse().setStatu("Error");
			getResponse().setInformation(new TeacherResponse("Teacher didn't send an answer image for that question"));
			return new ResponseEntity<>(getResponse(), HttpStatus.NOT_FOUND);
		}
		LOGGER.debug("Answer image byte is null which states that teacher doesn't want to update image. Maybe audio only!!!");
		return getNextHandler().handle(request);
	}
	
}
