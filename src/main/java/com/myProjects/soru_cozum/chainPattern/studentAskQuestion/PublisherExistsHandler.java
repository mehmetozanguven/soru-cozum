package com.myProjects.soru_cozum.chainPattern.studentAskQuestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.myProjects.soru_cozum.response.StudentAskQuestionResponse;

public class PublisherExistsHandler extends StudentAskQuestionAbstractHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublisherExistsHandler.class);

	@Override
	public ResponseEntity<?> handle(StudentAskQuestionRequest request) {
		LOGGER.debug("2. Check publisher exists or not");
		if (!request.getPublisher().isPresent()) {
			LOGGER.debug(
					"Publisher not exists, that's means this is unknown publisher, because publisher must be known before adding request");

			getResponse().setStatu("Error");
			getResponse().setInformation(new StudentAskQuestionResponse("Invalid publisher ID"));
			return new ResponseEntity<>(getResponse(), HttpStatus.OK);
		} else {
			LOGGER.debug("Publisher exists, then go to next cycle -check student ask that question before-");
			return getNextHandler().handle(request);
		}
	}
}
