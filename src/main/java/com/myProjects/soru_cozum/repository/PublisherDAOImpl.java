package com.myProjects.soru_cozum.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myProjects.soru_cozum.model.Publisher;

@Repository
public class PublisherDAOImpl implements PublisherDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public Optional<Publisher> findById(Long publisherId) {
		Session currentSes = entityManager.unwrap(Session.class);
		Publisher publisher = currentSes.get(Publisher.class, publisherId);
		
		return Optional.ofNullable(publisher);
	}
	
	@Override
	public Optional<Long> registerNewPublisher(Publisher newPublisher) {
		Session currentSess = entityManager.unwrap(Session.class);
		Long publisherId = (Long) currentSess.save(newPublisher);
		if (publisherId == 0)
			return Optional.empty();
		return Optional.ofNullable(publisherId);
	}
}
