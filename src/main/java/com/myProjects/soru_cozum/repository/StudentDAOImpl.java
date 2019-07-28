package com.myProjects.soru_cozum.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;

@Component
public class StudentDAOImpl implements StudentDAO{
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
	
}
