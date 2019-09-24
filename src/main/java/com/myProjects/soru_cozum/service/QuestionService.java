package com.myProjects.soru_cozum.service;

import java.util.List;
import java.util.Optional;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Teacher;

public interface QuestionService {
	
	Optional<Question> findQuestionById(long questionId);

	Optional<Question> findQuestionByPageNumber_QuestionNumber_Publisher(int pageNumber, int questionNumber, Publisher publisher, String questionCategory, String questionSubCategory);

	QuestionImage createNewQuestionImage(byte[] imageByte);
	
	List<Question> getAllNonAnsweredQuestionsBySpecificType(QuestionCategory questionCategory);
	
	QuestionImage getQuestionImageByQuestionId(Long questionId);
	
	void updateQuestion(Question question);

	Question createNewQuestionWithCommonProperties(int pageNumber, int questionNumber,
			String questionCategory, String questionSubCategory, byte[] questionImageByte);
	
	Question createNewQuestionWithCommonProperties_multipart(int pageNumber, int questionNumber,
			String questionCategory, String questionSubCategory);
	
	void addPublisherToQuestionn(Question question, Publisher publisher);

	Teacher findTeacherFromQuestion(Long questionId, Long teacherId);
}
