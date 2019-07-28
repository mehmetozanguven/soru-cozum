package com.myProjects.soru_cozum.repository;

import java.util.Optional;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;

public interface QuestionDAO {

	Optional<Question> findQuestionByPageNumberQuestionNumberPublisher(int pageNumber, int questionNumber,
			Publisher publisher);

}
