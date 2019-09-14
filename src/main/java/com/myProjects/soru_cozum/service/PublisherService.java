package com.myProjects.soru_cozum.service;

import com.myProjects.soru_cozum.model.Publisher;

public interface PublisherService {
	Publisher findById(long publisherId);
	
	void createNewPublisher(Publisher publisher, Publisher newPublisher);
	
	Publisher createNewPublisherFromRequest(Publisher requestPublisher);
}
