package com.myProjects.soru_cozum.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Teacher;

@Repository
public class QuestionDAOImpl implements QuestionDAO{
	private final static Logger LOGGER = LoggerFactory.getLogger(QuestionDAOImpl.class);
	
	@Autowired
	private EntityManager entityManager;
	
	@Value("${app.model.question}")
	private String modelQuestion;
	
	@Value("${app.model.teacher}")
	private String modelTeacher;
	
	@Override
	public Optional<Question> findQuestionById(long questionId) {
		Session currentSess = entityManager.unwrap(Session.class);
		String hibernateQuery = 
				"from Question q where q.id = :questionId";
		TypedQuery<Question> query = currentSess.createQuery(hibernateQuery, Question.class);
		query.setParameter("questionId", questionId);
		
		List<Question> resultList = query.getResultList();
		if (!resultList.isEmpty())
			return Optional.of(resultList.get(0));
		return Optional.empty();
	}
	
	@Override
	public void updateQuestion(Question question) {
		Session currentSess = entityManager.unwrap(Session.class);
		currentSess.update(question);
	}
	
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
	
	
	@Override
	public List<Question> getAllNonAnsweredQuestionsBySpecificType(String questionType) {
		Session currentSess = entityManager.unwrap(Session.class);
		
		String hibernateQuery = 
				"from Question q where q.questionCategory = :value and q.isAnswered = false";
		TypedQuery<Question> query = currentSess.createQuery(hibernateQuery, Question.class);
		query.setParameter("value", questionType);
		List<Question> resultList = query.getResultList();
		LOGGER.debug("All Specific type questions: " + resultList);
		
		return resultList;
	}
	
	@Override
	public QuestionImage getQuestionImageByQuestionId(Long questionId) {
		Session currentSess = entityManager.unwrap(Session.class);
		
		String hibernateQuery = 
				"select q.questionImage from Question q where q.id = :questionId";
		
		TypedQuery<QuestionImage> query = currentSess.createQuery(hibernateQuery, QuestionImage.class);
		query.setParameter("questionId", questionId);
		List<QuestionImage> resultList = query.getResultList();
		if (!resultList.isEmpty())
			return resultList.get(0);
		return null;
	}
	
	@Override
	public Optional<Teacher> findTeacherFromQuestionId(Long questionId, Long teacherId) {
		Question question = new Question();
		Session currentSess = entityManager.unwrap(Session.class);
		
		String hibernateQuery = 
			"SELECT q.teacherSet from Question q JOIN q.teacherSet t where q.id = :questionId and t.id = :teacherId";
		
		Query<Object[]> query = currentSess.createQuery(hibernateQuery);
		query.setParameter("questionId", questionId);
		query.setParameter("teacherId", teacherId);
		List<Object[]> resultList = query.getResultList();
		
		Teacher teacher = null;
		for (Object each : resultList) {
			if (each.getClass().getSimpleName().equals(modelTeacher)) {
				teacher = (Teacher) each;
			}
		}
		LOGGER.debug("result list: " + resultList);
		
		return Optional.of(teacher);
	}
}
