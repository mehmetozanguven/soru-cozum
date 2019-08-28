package com.myProjects.soru_cozum.service;

import java.util.List;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.request.AddQuestionRequest;

public interface QuestionService {
	
	Question findQuestionById(long questionId);

	Question findQuestionByPageNumberQuestionNumberPublisher(int pageNumber, int questionNumber, Publisher publisher);

	QuestionImage createNewQuestionImage(byte[] imageByte);
	
	List<Question> getAllNonAnsweredQuestionsBySpecificType(QuestionCategory questionCategory);
	
	QuestionImage getQuestionImageByQuestionId(Long questionId);
	
	void updateQuestion(Question question);

	Question createNewQuestionWithCommonProperties(int pageNumber, int questionNumber,
			QuestionCategory questionCategory, String questionSubCategory, byte[] questionImageByte);
	
	void addPublisherToQuestionn(Question question, Publisher publisher);

	Teacher findTeacherFromQuestion(Long questionId, Long teacherId);
}
