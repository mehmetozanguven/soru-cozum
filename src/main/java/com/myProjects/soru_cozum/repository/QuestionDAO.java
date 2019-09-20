package com.myProjects.soru_cozum.repository;

import java.util.List;
import java.util.Optional;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Teacher;

public interface QuestionDAO {

	Optional<Question> findQuestionByPageNumberQuestionNumberPublisher(int pageNumber, int questionNumber,
			Publisher publisher, String questionCategory, String questionSubCategory);

	List<Question> getAllNonAnsweredQuestionsBySpecificType(String value);

	QuestionImage getQuestionImageByQuestionId(Long questionId);

	Optional<Question> findQuestionById(long questionId);
	
	void updateQuestion(Question question);

	Optional<Teacher> findTeacherFromQuestionId(Long questionId, Long teacherId);

}
