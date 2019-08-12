package com.myProjects.soru_cozum.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myProjects.soru_cozum.model.AnswerImage;

@Repository
public class AnswerDAOImpl implements AnswerDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	public void deleteAnswerImage(AnswerImage deletedImage) {
		Session currentSess = entityManager.unwrap(Session.class);
		currentSess.delete(deletedImage);
	}
}
