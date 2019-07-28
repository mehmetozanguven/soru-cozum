package com.myProjects.soru_cozum.service;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.request.AddQuestionToStudentRequest;

public interface QuestionService {

	Question findQuestionByPageNumberQuestionNumberPublisher(int pageNumber, int questionNumber, Publisher publisher);

	QuestionImage createNewQuestionImage(byte[] imageByte);

	Question addQuestionImageToQuestion(Question question, QuestionImage questionImage);

	Question addPublisherToQuestion(Question question, Publisher publisher);
	
	Question addPageNumberToQuestion(Question question, int pageNumber);
	
	Question addQuestionNumberToQuestion(Question question, int questionNumber);
	
	Question addIsAnsweredProperty(Question question, boolean isAnswered);
}
