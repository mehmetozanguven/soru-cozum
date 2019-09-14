package com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.AnswerAudio;
import com.myProjects.soru_cozum.response.TeacherResponse;

public class TeacherOldAnswerAudioHandler extends TeacherUpdateAnswerAbstactHandler{
	private final Logger LOGGER = LoggerFactory.getLogger(TeacherOldAnswerAudioHandler.class);

	@Override
	public ResponseEntity<?> handle(TeacherUpdateAnswerRequest request) {
		if (request.getUserRequest().getAudioByte()!= null) {
			Optional<AnswerAudio> answerVideo = request.getTeacherService().findAnswerAudioFromTeacher(request.getUserRequest().getTeacherId(), request.getUserRequest().getQuestionId());
			
			if (answerVideo.isPresent()) {
				request.setOldAnswerAudio(answerVideo.get());
				getNextHandler().handle(request);
			}
			getResponse().setStatu("Error");
			getResponse().setInformation(new TeacherResponse("Teacher didn't send an answer video for that question"));
			return new ResponseEntity<>(getResponse(), HttpStatus.NOT_FOUND);
		}
		LOGGER.debug("Answer video byte is null which states that teacher doesn't want to update video");
		return getNextHandler().handle(request);
	}
	
}
