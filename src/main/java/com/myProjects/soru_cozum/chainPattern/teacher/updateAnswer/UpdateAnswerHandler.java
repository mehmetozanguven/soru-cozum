package com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myProjects.soru_cozum.model.AnswerAudio;
import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.response.TeacherResponse;

public class UpdateAnswerHandler extends TeacherUpdateAnswerAbstactHandler {

	@Override
	public ResponseEntity<?> handle(TeacherUpdateAnswerRequest request) {
		getResponse().setStatu("Success");
		if (request.getOldAnswerImage() != null) {
			AnswerImage newAnswerImage = new AnswerImage();
			request.getTeacherService().updateTeacherAnswerImage(request.getTeacher(), request.getOldAnswerImage(),
					newAnswerImage);
		}
		if (request.getOldAnswerAudio() != null) {
			AnswerAudio newAnswerAudio = new AnswerAudio();
			request.getTeacherService().updateTeacherAnswerAudio(request.getTeacher(), request.getOldAnswerAudio(),
					newAnswerAudio);
		}
		if (request.getOldAnswerImage() == null && request.getOldAnswerAudio() == null) {
			getResponse().setInformation(new TeacherResponse("No content updated, because request's parameters are null"));
			return new ResponseEntity<>(getResponse(), HttpStatus.OK);

		}
		getResponse().setInformation(new TeacherResponse("Teacher's new answer updated"));

		return new ResponseEntity<>(getResponse(), HttpStatus.OK);
	}

}
