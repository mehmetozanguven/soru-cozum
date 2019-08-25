package com.myProjects.soru_cozum.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;

@Repository
public class StudentDAOImpl implements StudentDAO{
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOImpl.class);	

	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Optional<Student> findStudentById(Long studentId) {
		Session currentSes = entityManager.unwrap(Session.class);
		Student student = currentSes.get(Student.class, studentId);
		
		return Optional.ofNullable(student);
	}

	@Override
	public void addQuestionToStudent(Student student, Question question) {
		student.addQuestionToStudent(question);
		Session currentSes = entityManager.unwrap(Session.class);
		currentSes.save(student);
	}
	
	@Override
	public void updateStudent(Student student) {
		Session currentSes = entityManager.unwrap(Session.class);
		currentSes.update(student);
	}
	
	@Override
	public Long registerNewStudent(Student student) {
		Session currentSes = entityManager.unwrap(Session.class);
		Long studentId = (Long) currentSes.save(student);
		return studentId;
	}
	
	@Override
	public boolean checkStudentExistsWithUsernameAndPassword(String studentName, String studentPassword) {
		Session currentSes = entityManager.unwrap(Session.class);
		String hibernateQuery = "from Student s where s.name = :studentName and s.password = :studentPassword";
		TypedQuery<Student> query = currentSes.createQuery(hibernateQuery, Student.class);
		query.setParameter("studentName", studentName);
		query.setParameter("studentPassword", studentPassword);
		List<Student> resultList = query.getResultList();
		
		LOGGER.debug("check student exists by username and password: " + resultList);
		
		return !resultList.isEmpty();
	}
	
	@Override
	public Optional<Student> findByUsernameAndPassword(String username, String password) {
		Student student = null;
		Session currentSes = entityManager.unwrap(Session.class);
		String hibernateQuery = "from Student s where s.name = :studentName and s.password = :studentPassword";
		
		TypedQuery<Student> query = currentSes.createQuery(hibernateQuery, Student.class);
		query.setParameter("studentName", username);
		query.setParameter("studentPassword", password);
		student = query.getSingleResult();
		return Optional.ofNullable(student);
	}
	
	@Override
	public Optional<Student> findByUsername(String username) {
		Student student = null;
		Session currentSes = entityManager.unwrap(Session.class);
		String hibernateQuery = "select s from Student s where s.username = :studentUsername";
		
		TypedQuery<Student> query = currentSes.createQuery(hibernateQuery, Student.class);
		query.setParameter("studentUsername", username);
		
		try {
			student = query.getSingleResult();
		}catch (NonUniqueResultException e) {
			LOGGER.error("findTeacherByUsername didn't return one result which should have been. Look this method !!!!!");
		} catch (NoResultException e) {
			LOGGER.error("findTeacherByUsername didn't found a record with that username which could be possible");
		}
		return Optional.ofNullable(student);
	}
	
}
