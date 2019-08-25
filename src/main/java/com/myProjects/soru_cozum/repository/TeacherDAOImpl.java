package com.myProjects.soru_cozum.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.model.Teacher;

@Repository
public class TeacherDAOImpl implements TeacherDAO{
	private final static Logger LOGGER = LoggerFactory.getLogger(TeacherDAOImpl.class);
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Optional<Teacher> findTeacherById(long teacherId) {
		Session currentSess = entityManager.unwrap(Session.class);
		String hibernateQuery = 
				"from Teacher t where t.id = :teacherId";
		TypedQuery<Teacher> query = currentSess.createQuery(hibernateQuery, Teacher.class); 
		query.setParameter("teacherId", teacherId);
		Teacher teacher = query.getSingleResult();
		/*List<Teacher> resultList = query.getResultList();
		if (resultList.isEmpty())
			return Optional.empty();
		*/
		return Optional.ofNullable(teacher);
	}
	
	/*@Override
	public boolean findTeacherByUsername(String name, String password) {
		Session currentSess = entityManager.unwrap(Session.class);
		String hibernateQuery = 
				"select t from Teacher t where t.surname = :name and t.password = :password";
		TypedQuery<Teacher> query = currentSess.createQuery(hibernateQuery, Teacher.class);
		query.setParameter("name", name);
		query.setParameter("password", password);
		
		Teacher teacher = null;
		try {
			teacher = query.getSingleResult();
		}catch (NoResultException e) {
			teacher = null;
		}
		LOGGER.debug("checksTeacherExistsWithUsernameAndPassword teacher: " + teacher);
		return !(teacher == null);
	}*/
	
	@Override
	public void registerNewTeacher(Teacher teacher) {
		Session currentSess = entityManager.unwrap(Session.class);
		currentSess.save(teacher);
	}
	
	@Override
	public void updateTeacher(Teacher teacher) {
		Session currentSess = entityManager.unwrap(Session.class);
		currentSess.update(teacher);
	}
	
	@Override
	public Optional<AnswerImage> getAnswerImageFromTeacher(Long teacherId, Long questionId) {
		Session currentSess = entityManager.unwrap(Session.class);
		String hibernateQuery = 
				"select im from AnswerImage im where im.teacher.id = :teacherId and im.associatedQuestionId = :questionId";
		Query query = currentSess.createQuery(hibernateQuery);
		query.setParameter("questionId", questionId);
		query.setParameter("teacherId", teacherId);
		
		List<AnswerImage> resultList = query.getResultList();
		LOGGER.debug("result List of answered image: " + resultList);
		
		AnswerImage answerImage = null;
		for (AnswerImage eachObject : resultList) {
			LOGGER.debug("each object: " + eachObject);
			answerImage = (AnswerImage) eachObject;	
		}
		return Optional.ofNullable(answerImage);
	}
	
	@Override
	public Optional<Teacher> findTeacherByUsername(String username){
		Session currentSess = entityManager.unwrap(Session.class);
		String hibernateQuery = "select t from Teacher t where t.username = :UserName";
		TypedQuery<Teacher> query = currentSess.createQuery(hibernateQuery);
		query.setParameter("UserName", username);
		Teacher teacher = null;
		try {
			teacher = query.getSingleResult();
		}catch (NonUniqueResultException e) {
			LOGGER.error("findTeacherByUsername didn't return one result which should have been. Look this method");
		} catch (NoResultException e) {
			LOGGER.error("findTeacherByUsername didn't found a record with that username which could be possible");
		}
		
		return Optional.ofNullable(teacher);
	}
}
