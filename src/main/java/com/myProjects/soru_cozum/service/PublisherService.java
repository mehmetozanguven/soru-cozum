package com.myProjects.soru_cozum.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.repository.PublisherDAO;

@Service
@Transactional
public class PublisherService {
	@Autowired
	private PublisherDAO publisherDAO;
	

	public Publisher findById(long publisherId) {
	
		Optional<Publisher> publisher = publisherDAO.findById(publisherId);
			
		return publisher.orElse(new Publisher("nonce"));
	}
	
	
	public void createNewPublisher(Publisher publisher, Publisher newPublisher) {
		publisher.setName(newPublisher.getName());
		publisher.setPublishYear(newPublisher.getPublishYear());
	}
	
	public Publisher createNewPublisherFromRequest(Publisher requestPublisher) {
		Publisher newPublisher = new Publisher();
		newPublisher.setName(requestPublisher.getName());
		newPublisher.setPublishYear(requestPublisher.getPublishYear());
		return newPublisher;
	}
	
	
}
