package com.myProjects.soru_cozum.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myProjects.soru_cozum.model.Teacher;

@Repository
public class TeacherDAOImpl implements TeacherDAO{
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public Optional<Teacher> getTeacherById(long teacherId) {
		Session currentSess = entityManager.unwrap(Session.class);
		String hibernateQuery = 
				"from Teacher t where t.id = :teacherId";
		TypedQuery<Teacher> query = currentSess.createQuery(hibernateQuery, Teacher.class); 
		query.setParameter("teacherId", teacherId);
		List<Teacher> resultList = query.getResultList();
		if (resultList.isEmpty())
			return Optional.empty();
		
		return Optional.of(resultList.get(0));
	}
	
	
	@Override
	public boolean checksTeacherExistsWithUsernameAndPassword(String name, String password) {
		Session currentSess = entityManager.unwrap(Session.class);
		String hibernateQuery = 
				"from Teacher t where t.name = :name and t.password = :password";
		TypedQuery<Teacher> query = currentSess.createQuery(hibernateQuery, Teacher.class);
		query.setParameter("name", name);
		query.setParameter("password", password);
		
		List<Teacher> resultList = query.getResultList();
		
		return !resultList.isEmpty();
	}
	
	@Override
	public void registerNewTeacher(Teacher teacher) {
		Session currentSess = entityManager.unwrap(Session.class);
		currentSess.save(teacher);
	}
	
}
