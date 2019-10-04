package com.myProjects.soru_cozum.service;

import java.time.YearMonth;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.repository.PublisherDAO;

@Service
@Transactional
public class PublisherServiceImpl implements PublisherService{
	@Autowired
	private PublisherDAO publisherDAO;
	
	@Override
	public Optional<Publisher> findById(long publisherId) {
		Optional<Publisher> publisher = publisherDAO.findById(publisherId);			
		return publisher;
	}
	
	@Override
	public Optional<Long> registerNewPublisher(Publisher newPublisher){
		return publisherDAO.registerNewPublisher(newPublisher);
	}
	
	@Override
	public void createNewPublisher(Publisher publisher, Publisher newPublisher) {
		publisher.setName(newPublisher.getName());
		publisher.setPublishYear(newPublisher.getPublishYear());
	}
	
	@Override
	public Publisher createNewPublisherFromRequest(Publisher requestPublisher) {
		Publisher newPublisher = new Publisher();
		newPublisher.setName(requestPublisher.getName());
		newPublisher.setPublishYear(requestPublisher.getPublishYear());
		return newPublisher;
	}
	
	@Override
	public Publisher createUnknownPublisher() {
		Publisher unknownPublisher = new Publisher();
		unknownPublisher.setName("unknown");
		unknownPublisher.setPublishYear(YearMonth.now().getYear());
		return unknownPublisher;
	}
	
}
