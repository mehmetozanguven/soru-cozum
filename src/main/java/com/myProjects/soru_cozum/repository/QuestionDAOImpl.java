package com.myProjects.soru_cozum.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Student;

@Repository
public class QuestionDAOImpl implements QuestionDAO{
	
	@Autowired
	private EntityManager entityManager;
	
	@Value("${app.model.question}")
	private String modelQuestion;
	
	@Override
	public Optional<Question> findQuestionByPageNumberQuestionNumberPublisher(int pageNumber, int questionNumber,
			Publisher publisher) {
		Question question = new Question();
		Session currentSess = entityManager.unwrap(Session.class);
		
		String hibernateQuery = 
			"from Question q LEFT OUTER JOIN q.publisher pub where q.pageNumber = :pageNumber and "
				+ "q.questionNumber = :questionNumber";
		
		Query<Object[]> query = currentSess.createQuery(hibernateQuery);
		query.setParameter("pageNumber", pageNumber);
		query.setParameter("questionNumber", questionNumber);
		List<Object[]> queryResult = query.getResultList();
		
		for (Object[] eachObject : queryResult) {
			for (int i = 0; i < eachObject.length; i++) {
				if (eachObject[i] != null && eachObject[i].getClass().getSimpleName().equals(modelQuestion))
					question = (Question) eachObject[i];
			}
			
		}
		
		if (question.getId() == null) {
			question.setId((long)0);
			return Optional.of(question);
		}
			
		return Optional.of(question);
	}
}
