package com.myProjects.soru_cozum.service;

import java.util.Optional;

import com.myProjects.soru_cozum.model.Publisher;

public interface PublisherService {
	Optional<Publisher> findById(long publisherId);

	void createNewPublisher(Publisher publisher, Publisher newPublisher);

	Publisher createNewPublisherFromRequest(Publisher requestPublisher);
	
	Publisher createUnknownPublisher();
}
